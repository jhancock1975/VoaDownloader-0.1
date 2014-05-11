package com.rootser;

import android.os.Bundle;

import com.google.inject.Inject;
import com.rootser.service.URLs;

import roboguice.inject.InjectResource;

/**
 * Created by john on 5/10/14.
 */
public class DownloadBundleWrapper {
    Bundle bundle;
    @InjectResource(R.id.main_activity_url_rsrc_bundle_key)
    private String urlBundleKey;
    @InjectResource(R.id.main_activity_dest_dir_key)
    private String destDirNameKey;
    @InjectResource(R.id.dest_file_key)
    private String destFileNameKey;
    @Inject
    private URLs urls;
    DownloadBundleWrapper(){
        bundle = new Bundle();
        bundle.putStringArray(urlBundleKey, urls.getUrls());
        bundle.putString(destDirNameKey, "/storage/emulated/0/Download");
        bundle.putString(destFileNameKey, "mand2200a.mp3");
    }
    public Bundle getBundle(){
        return bundle;
    }
}
