package com.rootser.service;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.google.inject.Inject;
import com.rootser.DownloadMessage;
import com.rootser.DownloadStatus;
import com.rootser.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import roboguice.inject.InjectResource;

/**
 * Created by john on 4/29/14.
 */
public class DownloadWebPageTask extends AsyncTask<String, Void, DownloadMessage> {

    private String DEBUG_TAG = "DownloadWebPageTask";
    @InjectResource(R.string.network_not_available)
    String no_network;
    @InjectResource(R.string.file_not_available)
    String no_file;
    @Inject
    private ConnectivityManager connMgr;
    @Inject
    private DownloadMessage result;

    private DownloadService callingService;

    public void setCallingService(DownloadService callingService) {
        this.callingService = callingService;
    }

    @Override
    protected DownloadMessage doInBackground(String... urls) {
        // params comes from the execute() call: params[0] is the url.
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                result.setStatus(downloadUrl(urls[0]));
            } catch (IOException e) {
                result.setStatus(DownloadStatus.IO_EXCEPTION);
            }
        } else {
            result.setStatus(DownloadStatus.NO_NETWORK);
        }
        return result;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(DownloadMessage result) {
        callingService.displayNotification();

    }

    private DownloadStatus downloadUrl(String myurl) throws IOException {
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
            File downloadedFilesDir = new File("/storage/emulated/0/Download");
            File file = new File(downloadedFilesDir, "mand2200a.mp3");
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
        return DownloadStatus.COMPLETE;
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream inputStream, int totalSize)
            throws IOException, UnsupportedEncodingException {

        byte[] buffer = new byte[1024];
        File downloadedFilesDir = new File("/storage/emulated/0/Download");
        File file = new File(downloadedFilesDir, "mand2200a.mp3");
        FileOutputStream fileOutput = new FileOutputStream(file);
        int downloadedSize = 0;
        int bufferLength = 0; // used to store a temporary size of the
        // buffer
        while ((bufferLength = inputStream.read(buffer)) > 0)

        {

            // add the data in the buffer to the file in the file output
            // stream (the file on the sd card

            fileOutput.write(buffer, 0, bufferLength);

            // add up the size so we know how much is downloaded

            downloadedSize += bufferLength;

            int progress = (int) (downloadedSize * 100 / totalSize);

            // this is where you would do something to report the prgress,
            // like this maybe

            // updateProgress(downloadedSize, totalSize);

        }

        // close the output stream when done

        fileOutput.close();
        return "complete";
    }

}