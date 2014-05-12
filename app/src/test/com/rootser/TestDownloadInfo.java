package com.rootser;

import java.io.Serializable;

/**
 * Created by john on 5/10/14.
 */
public class TestDownloadInfo implements Serializable, DownloadInfoInf {
    private String url;
    private String destFileName;
    private String destFileDir;

    TestDownloadInfo() {
        destFileDir = "/storage/emulated/0/test";
        destFileName = "test.txt";
        url = "http://stockman.rootser.com/stockman/test.txt";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public void setDestFileName(String destFileName) {
        this.destFileName = destFileName;
    }

    public String getDestFileDir() {
        return destFileDir;
    }

    public void setDestFileDir(String destFileDir) {
        this.destFileDir = destFileDir;
    }
}
