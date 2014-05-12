package com.rootser;

import android.content.Context;
import android.content.Intent;
import android.test.ServiceTestCase;

import com.rootser.service.DownloadService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 5/10/14.
 */
public class DownloadServiceTest extends ServiceTestCase {
   private static String DEBUG_TAG = DownloadServiceTest.class.getName();
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
        TestDownloadInfo info = new TestDownloadInfo();

        List<DownloadInfoInf> infoList = new ArrayList<DownloadInfoInf>();
        infoList.add(info);

        Context ctx = getContext();

        DownloadService downloadService;

        Intent intent = new Intent(getContext(), DownloadService.class);
        intent.putExtra(getContext().getString(R.string.download_info_intent_key),
                (ArrayList<DownloadInfoInf>) infoList);
        startService(intent);

        boolean fileDownloaded = false;
        boolean testComplete = false;
        File testFile = new File (info.getDestFileDir() + "/" + info.getDestFileName());
        //I found it was better to delete the file rather than check the time on it
        //because the create time on the files appears to be rounded down to the
        //nearest second when running (as opposed to debugging) this code.
        //Hence file create times were before test start times, even though files were
        //actually created after the tests began.
        testFile.delete();
        int loopCount = 0;
        int maxIterations = 60;
        while ( ! testComplete){
            fileDownloaded = testFile.exists();
            testComplete = fileDownloaded || loopCount > maxIterations;
            Thread.sleep(1000);
            loopCount++;
        }
        assertTrue("it took too long to try and download the file" ,
                fileDownloaded && (loopCount < maxIterations));
    }
}
