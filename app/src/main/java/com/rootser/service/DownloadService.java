package com.rootser.service;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.inject.Inject;
import com.rootser.MainActivity;
import com.rootser.R;

import roboguice.service.RoboIntentService;

/**
 * Created by john on 5/3/14.
 */

public class DownloadService extends RoboIntentService {

    @Inject
    private  DownloadWebPageTask downloadTask;
    @Inject
    private  URLs urls;

    public DownloadService() {
        super("DownloadService");
    }
    int mId = 0;
    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            downloadTask.setCallingService(this);
            for (String url : urls.getUrls()) {
               downloadTask.execute(url);
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void displayNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("FileFisher")
                        .setContentText(getString(R.string.complete));
// Creates an explicit intent for an Activity in your app
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