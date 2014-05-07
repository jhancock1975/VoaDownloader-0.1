package com.rootser;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class MainActivity extends RoboActivity {
    @InjectView(R.id.downloadStatusText)
    private TextView textView;
    @InjectView(R.id.downloadButton)
    private Button downloadButton;
    @Inject
    DownloadButtonClickListener downloadButtonClickListener;

    private void setupControls(){
        textView.setText(R.string.ready);
        downloadButton.setOnClickListener(downloadButtonClickListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupControls();
    }
}
