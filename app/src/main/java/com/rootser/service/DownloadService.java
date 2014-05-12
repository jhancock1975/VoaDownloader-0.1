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
import com.rootser.DownloadInfoInf;
import com.rootser.DownloadStatus;
import com.rootser.MainActivity;
import com.rootser.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectResource;
import roboguice.service.RoboIntentService;

/**
 * Created by john on 5/3/14.
 */

public class DownloadService extends RoboIntentService {

    @Inject
    private ConnectivityManager connMgr;
    private NetworkInfo networkInfo;

    @InjectResource(R.string.download_info_intent_key)
    private String downloadInfoIntentKey;

    @Inject
    private DownloadStatus downloadStatus;

    private List<DownloadNoteInfo> fileMessages;
    private final static String DEBUG_TAG = DownloadService.class.getName();

    public DownloadService() {
        super("DownloadService");
        fileMessages = new ArrayList<DownloadNoteInfo>();
    }

    int mId = 0;

    @Override
    protected void onHandleIntent(Intent intent) {
        List<DownloadInfoInf> infoList = (List<DownloadInfoInf> )
                intent.getSerializableExtra(downloadInfoIntentKey);
        synchronized (this) {
            for (DownloadInfoInf info : infoList) {
                networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    try {
                        fileMessages.add(downloadUrl(info));
                    } catch(IOException e){
                        fileMessages.add(getIoExceptionNote(info));
                    }
                } else {
                    fileMessages.add(getNoNetworkNote(info));
                }
            }try{
                displayNotification(fileMessages);
            } catch(Exception e){
                e.printStackTrace();
                Log.d(DEBUG_TAG, e.getMessage());
            }
        }
    }

    /**
     * all DownloadNoteInfo objects need file name, url, and time
     * so code is factorted out here.
     * @param info
     * @return
     */
    DownloadNoteInfo getFileUrlTime(DownloadInfoInf info){
        File downloadedFilesDir = new File(info.getDestFileDir());
        File file = new File(downloadedFilesDir, info.getDestFileName());
        DownloadNoteInfo dnInfo = new DownloadNoteInfo()
                .setDownloadTime(System.currentTimeMillis())
                .setFileName(file.getName());
        try {
            URL url = new URL(info.getUrl());
            dnInfo.setSiteName(url.getHost());
        } catch(MalformedURLException e){
            Log.d(DEBUG_TAG, e.getMessage());
            dnInfo.setSiteName("");
        } catch(Exception e){
            Log.d(DEBUG_TAG, e.getMessage());
            Log.d(DEBUG_TAG, "site name didn't match second_level.top_level domain name pattern.");
        }
        return dnInfo;
    }
    DownloadNoteInfo getIoExceptionNote(DownloadInfoInf info){
        return  getFileUrlTime(info).setStatus(downloadStatus.ioExceptionStr);
    }
    DownloadNoteInfo getNoNetworkNote(DownloadInfoInf info){
        return getFileUrlTime(info).setStatus(downloadStatus.noNetStr);
    }
    /**
     * this method will download the url at myurl
     * to the file named downloadFileName in the directory downloadedFilesDirName
     *
     * @param info - object holding information about what to download
     *                       and where to store it
     * @return DownloadNoteInfo object for use with notification
     * @throws IOException
     */
    private DownloadNoteInfo downloadUrl(DownloadInfoInf info) throws IOException {
        InputStream is = null;
        File file;
        DownloadNoteInfo dnInfo;
        try {
            URL url = new URL(info.getUrl());
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
            File downloadedFilesDir = new File(info.getDestFileDir());
            file = new File(downloadedFilesDir, info.getDestFileName());

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
            dnInfo = getFileUrlTime(info)
                    .setStatus(downloadStatus.completeStr);
        } finally {
            if (is != null) {
                is.close();
            }
        }
      return dnInfo;
    }

    /*
    strings used in displaying notifications
     */
    @InjectResource(R.string.notificationBigContentTitle)
    private String bigContentTitle;

    @InjectResource(R.string.app_name)
    private String notificationTitle;

    @InjectResource(R.string.note_actions_performed)
    private String noteActionsPerformed;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    /**
     * this method is responsible for displaying
     * notifications to the user on the results
     * of the service's attempts to download files
     * The code below is from
     * http://developer.android.com/guide/topics/ui/notifiers/notifications.html
     */
    public void displayNotification(List<DownloadNoteInfo> fileMessages) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(notificationTitle)
                        .setContentText(noteActionsPerformed);

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(bigContentTitle);

        for (DownloadNoteInfo dnInfo: fileMessages){
            inboxStyle.addLine(dnInfo.toString());
        }
        mBuilder.setStyle(inboxStyle);
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(++mId, mBuilder.build());
    }
}