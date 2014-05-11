package com.rootser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.ServiceTestCase;
import android.util.Log;

import com.rootser.service.DownloadService;

import java.io.File;

/**
 * Created by john on 5/10/14.
 */
public class DownloadServiceTest extends ServiceTestCase {

    public DownloadServiceTest(Class serviceClass) {
        super(serviceClass);
    }
    public DownloadServiceTest(){
        super(DownloadService.class);
    }

    public void test() throws InterruptedException {

        TestURLs testUrls = new TestURLs();
        Bundle bundle = new Bundle();
        Context ctx = getContext();
        bundle.putStringArray(ctx.getString(R.id.main_activity_url_rsrc_bundle_key),
                testUrls.getUrls());
        String testDirName = "/storage/emulated/0/test";
        bundle.putString(ctx.getString(R.id.main_activity_dest_dir_key),testDirName);
        String testFileName = "test.txt";
        bundle.putString(ctx.getString(R.id.dest_file_key), testFileName);
        DownloadService downloadService;
        Intent intent = new Intent(getContext(), DownloadService.class);
        intent.putExtras(bundle);
        long startTestTime = System.currentTimeMillis();
        startService(intent);
        boolean fileDownloaded = false;
        boolean testComplete = false;
        File testFile = new File (testDirName + "/" + testFileName);
        int loopCount = 0;
        int maxIterations = 60;
        while ( ! testComplete){
            fileDownloaded = testFile.exists() && (testFile.lastModified() > startTestTime);
            testComplete = fileDownloaded || loopCount > maxIterations;
            try {
                /*
                 this is here because this test
                 fails if we try to use Thread.sleep() here
                 it might have something to do with there being
                 a synchronized block in the service we are testing.
                 I am not seing any error logged in logcat, but
                 the console reports a null pointer exception.
                 */
               this.wait(1000);
            } catch(Exception e){
                e.printStackTrace();;
                Log.d("download service test", "message " + e.getMessage());
                Log.d("download service test", "cause " + e.getCause());
            }
            loopCount++;
        }
        assertTrue("it took too long to try and download the file " ,
                fileDownloaded && (loopCount < maxIterations));
    }
}
