package com.rootser.service;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.inject.Inject;
import com.rootser.DownloadInfo;
import com.rootser.DownloadMessage;
import com.rootser.DownloadStatus;
import com.rootser.MainActivity;
import com.rootser.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import roboguice.inject.InjectResource;
import roboguice.service.RoboIntentService;

/**
 * Created by john on 5/3/14.
 */

public class DownloadService extends RoboIntentService {

    @Inject
    private ConnectivityManager connMgr;
    private NetworkInfo networkInfo;

    @Inject
    private DownloadMessage msg;

    @InjectResource(R.string.notificationBigContentTitle)
    private String bigContentTitle;

    @InjectResource(R.string.app_name)
    private String notificationTitle;

    @InjectResource(R.string.download_info_intent_key)
    private String downloadInfoIntentKey;

    private ArrayList<String> fileMessages;
    private final static String DEBUG_TAG = DownloadService.class.getName();

    public DownloadService() {
        super("DownloadService");
        fileMessages = new ArrayList<String>();
    }

    int mId = 0;

    @Override
    protected void onHandleIntent(Intent intent) {
        DownloadInfo downloadInfo = (DownloadInfo)
                intent.getSerializableExtra(downloadInfoIntentKey);
        synchronized (this) {
            for (String url : downloadInfo.getUrls().getUrls()) {
                networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    try {
                        fileMessages.add(downloadUrl(url,
                                downloadInfo.getDestFileDir(),
                                downloadInfo.getDestFileName()));
                    } catch(IOException e){
                        msg.setStatus(DownloadStatus.IO_EXCEPTION);
                    }
                } else {
                    msg.setStatus(DownloadStatus.NO_NETWORK);
                }
                msg.setStatus(DownloadStatus.COMPLETE);
            }try{
                displayNotification();
            } catch(Exception e){
                e.printStackTrace();
                Log.d(DEBUG_TAG, e.getMessage());
            }
        }
    }

    /**
     * this method will download the url at myurl
     * to the file named downloadFileName in the directory downloadedFilesDirName
     *
     * @param myurl - string holding url to download
     * @param downloadedFilesDirName - name of directory to save file to
     * @param downloadFileName - name of file to save to
     * @return string that is not getting used
     * @throws IOException
     */
    private String downloadUrl(String myurl, String downloadedFilesDirName,
                               String downloadFileName) throws IOException {
        InputStream is = null;
        DownloadStatus status;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();


            byte[] buffer = new byte[1024];
            File downloadedFilesDir = new File(downloadedFilesDirName);
            File file = new File(downloadedFilesDir, downloadFileName);
            FileOutputStream fileOutput = new FileOutputStream(file);
            int downloadedSize = 0;
            int bufferLength = 0;
            int totalSize = conn.getContentLength();
            while ((bufferLength = is.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                if (totalSize != 0 && totalSize != R.integer.NOT_SET) {
                    int progress = (int) (downloadedSize * 100 / totalSize);
                }
            }
            fileOutput.close();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        String urlEnd = myurl.substring(myurl.lastIndexOf('/'), myurl.length());
        return urlEnd + DownloadStatus.COMPLETE;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void displayNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(notificationTitle)
                        .setContentText(msg.getStatus().toString());
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(bigContentTitle);
// Creates an explicit intent for an Activity in your app
        for (String fileMsg: this.fileMessages){
            inboxStyle.addLine(fileMsg);
        }
        mBuilder.setStyle(inboxStyle);
        Intent resultIntent = new Intent(this, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(++mId, mBuilder.build());
    }
}