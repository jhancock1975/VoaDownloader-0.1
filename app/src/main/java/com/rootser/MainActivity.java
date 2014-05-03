package com.rootser;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;


public class MainActivity extends RoboActivity {
    @InjectView(R.id.text1)
    TextView textView;
    @InjectResource(R.string.hello_guice)
    String helloStr;
    @InjectView(R.id.downloadButton)
    Button downloadButton;
    @Inject
    DownloadButtonClickListener downloadButtonClickListener;

    private void setupControls(){
        textView.setText(helloStr);
        downloadButton.setOnClickListener(downloadButtonClickListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupControls();

    }

}
