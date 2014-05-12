package com.rootser;

import java.io.Serializable;

/**
 * Created by john on 5/10/14.
 */
public class DownloadInfo implements Serializable, DownloadInfoInf {
    private String url;
    private String destFileName;
    private String destFileDir;

    DownloadInfo() {
        destFileDir = "/storage/emulated/0/Download";
        destFileName = "mand2200a.mp3";
        url = "http://www.voanews.com/mp3/voa/eap/mand/mand2200a.mp3";
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
