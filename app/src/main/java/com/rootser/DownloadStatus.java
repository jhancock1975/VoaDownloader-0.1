package com.rootser;

import com.google.inject.Singleton;

import roboguice.inject.InjectResource;

/**
 * Created by john on 5/3/14.
 */
@Singleton
public class DownloadStatus {
    @InjectResource(R.string.ready)
    public String readyStr;
    @InjectResource(R.string.downloading)
    public String downloadingStr;
    @InjectResource(R.string.io_exception)
    public String ioExceptionStr;
    @InjectResource(R.string.network_not_available)
    public String noNetStr;
    @InjectResource(R.string.unknown)
    public String unkStr;
    @InjectResource(R.string.complete)
    public String completeStr;
}
