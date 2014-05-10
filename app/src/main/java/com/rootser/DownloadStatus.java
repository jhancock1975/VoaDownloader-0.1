package com.rootser;

/**
 * Created by john on 5/3/14.
 */
public enum DownloadStatus {
    READY, DOWNLOADING, IO_EXCEPTION, UNKNOWN, NO_NETWORK, COMPLETE;
    public int toResId(){
        switch(this){
            case READY:
                return R.string.ready;
            case DOWNLOADING:
                return R.string.downloading;
            case IO_EXCEPTION:
                return R.string.io_exception;
            case NO_NETWORK:
                return R.string.network_not_available;
            case COMPLETE:
                return R.string.complete;
            default:
                return R.string.unknown;
        }
    }
}
