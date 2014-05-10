package com.rootser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.inject.Inject;
import com.rootser.service.DownloadService;
import com.rootser.service.URLs;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;


public class MainActivity extends RoboActivity {

    @InjectView(R.id.downloadButton)
    private Button downloadButton;
    @InjectResource(R.id.main_activity_url_rsrc_bundle_key)
    private String urlBundleKey;
    @InjectResource(R.id.main_activity_dest_dir_key)
    private String destDirNameKey;
    @InjectResource(R.id.dest_file_key)
    private String destFileNameKey;

    @Inject
    private URLs urls;
    private void setupControls(){
        downloadButton.setOnClickListener(new DownloadButtonClickListener());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupControls();
    }

    public void startIntentService(){
        Intent intent = new Intent(this, DownloadService.class);

        intent.putExtra(urlBundleKey, urls.getUrls());
        intent.putExtra(destDirNameKey, "/storage/emulated/0/Download");
        intent.putExtra()
        startService(intent);
    }

    class DownloadButtonClickListener implements View.OnClickListener {
        @InjectView(R.id.downloadStatusText)
        private TextView textView;

        @Override
        public void onClick(View view) {
            startIntentService();
        }
    }
}
