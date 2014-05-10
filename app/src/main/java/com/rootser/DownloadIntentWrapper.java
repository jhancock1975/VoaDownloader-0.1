package com.rootser;

import android.os.Bundle;

import com.google.inject.Inject;
import com.rootser.service.URLs;

import roboguice.inject.InjectResource;

/**
 * Created by john on 5/10/14.
 */
public class DownloadIntentWrapper {
    Bundle bundle;
    @InjectResource(R.id.main_activity_url_rsrc_bundle_key)
    private String urlBundleKey;
    @InjectResource(R.id.main_activity_dest_dir_key)
    private String destDirNameKey;
    @InjectResource(R.id.dest_file_key)
    private String destFileNameKey;
    @Inject
    private URLs urls;
    DownloadIntentWrapper(){
        bundle = new Bundle();
        bundle.putStringArray(urlBundleKey, urls.getUrls());
        bundle.putString(destDirNameKey, )
    }
}
