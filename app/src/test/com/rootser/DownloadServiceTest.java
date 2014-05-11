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

    /**
     * this test tests the DownloadService
     * which expects an intent with a destination
     * directory, file name, and url, the contents of which
     * will be stored to a file
     * @throws InterruptedException
     */
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
        //I'm saving the start time here to compare wtih file create time
        long startTestTime = System.currentTimeMillis();
        startService(intent);
        boolean fileDownloaded = false;
        boolean testComplete = false;
        File testFile = new File (testDirName + "/" + testFileName);
        testFile.delete();
        int loopCount = 0;
        int maxIterations = 60;
        while ( ! testComplete){
            fileDownloaded = testFile.exists();
            //seems like, when running (as opposed to debugging) this code
            //file create times are rounded down to the nearest second
            Log.wtf("DownloadServiceTest" ,
                    "file " + testFile.lastModified() + " start " + startTestTime);
            testComplete = fileDownloaded || loopCount > maxIterations;
            Thread.sleep(1000);
            loopCount++;
        }
        assertTrue("it took too long to try and download the file" ,
                fileDownloaded && (loopCount < maxIterations));
    }
}
