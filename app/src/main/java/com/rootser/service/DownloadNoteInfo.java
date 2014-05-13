package com.rootser.service;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by john on 5/11/14.
 */
@DatabaseTable(tableName = "DownloadNoteInfo")
public class DownloadNoteInfo {
    @DatabaseField(id=true)
    private int dowloadNoteInfoId;
    @DatabaseField
    private String siteName;
    @DatabaseField
    private String fileName;
    @DatabaseField
    private long downloadTime;
    @DatabaseField
    private String status;


    public String getSiteName() {
        return siteName;
    }

    public DownloadNoteInfo setSiteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public DownloadNoteInfo setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public long getDownloadTime() {
        return downloadTime;
    }

    public DownloadNoteInfo setDownloadTime(long downloadTime) {
        this.downloadTime = downloadTime;

        return this;
    }

    public String getStatus() {
        return status;
    }

    public DownloadNoteInfo setStatus(String status) {
        this.status = status;
        return this;
    }
    /*
    Date formatting objects are static
    for reuse to cut down on resource consumption
    */
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    private static StringBuilder sb = new StringBuilder();
    private static Date date = new Date();
    public String toString(){
        date.setTime(downloadTime);
        sb.setLength(0);
        sb.append(fileName)
                .append(" ")
                .append(sdf.format(date))
                .append(" ")
                .append(status.toString());
        return sb.toString();
    }
}
