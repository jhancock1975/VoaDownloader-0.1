package com.rootser;

/**
 * Created by john on 5/3/14.
 */
public enum DownloadStatus {
    READY, DOWNLOADING, IO_EXCEPTION, UNKNOWN;
    public int toResId(){
        switch(this){
            case READY:
                return R.string.ready;
            case DOWNLOADING:
                return R.string.downloading;
            case IO_EXCEPTION:
                return R.string.io_exception;
            default:
                return R.string.unknown;
        }
    }
}
