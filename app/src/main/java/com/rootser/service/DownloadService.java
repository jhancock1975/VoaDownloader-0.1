package com.rootser.service;

import android.app.IntentService;
import android.content.Intent;

import com.google.inject.Inject;

/**
 * Created by john on 5/3/14.
 */
public class DownloadService extends IntentService {
    @Inject
    private DownloadWebPageTask downloadTask;
    @Inject
    private URLs urls;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            for (String url : urls.getUrls()) {
                downloadTask.execute(url);
            }
        }
    }
}