package com.rootser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rootser.service.DownloadService;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class MainActivity extends RoboActivity {
    @InjectView(R.id.downloadStatusText)
    private TextView textView;
    @InjectView(R.id.downloadButton)
    private Button downloadButton;


    private void setupControls(){
        textView.setText(R.string.ready);
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
