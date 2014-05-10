package com.rootser;

import android.content.Intent;
import android.test.ServiceTestCase;

import com.rootser.service.DownloadService;

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

    public void test(){
        TestURLs testUrls = new TestURLs();
        DownloadService downloadService;
        Intent intent = new Intent(getContext(), DownloadService.class);
        intent.putExtra(getContext().getString(R.id.main_activity_url_rsrc_bundle_key),
                testUrls.getUrls());
        intent.putExtra(getContext().getString(R.id.main_activity_dest_dir_key), )
        startService(intent);
    }
}