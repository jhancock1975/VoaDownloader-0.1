package com.rootser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.inject.Inject;
import com.rootser.service.DownloadService;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;


public class MainActivity extends RoboActivity {

    @InjectView(R.id.downloadButton)
    private Button downloadButton;

    @Inject
    private DownloadInfo info;

    @InjectResource(R.string.download_info_intent_key)
    private String downloadInfoIntentKey;

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
        List<DownloadInfoInf> infoList = new ArrayList<DownloadInfoInf>();
        infoList.add(info);
        intent.putExtra(downloadInfoIntentKey, (ArrayList<DownloadInfoInf>) infoList);
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
