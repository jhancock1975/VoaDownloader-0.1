package com.rootser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    @Inject
    NetworkStatus networkStatus;
    private void setupControls(){
        textView.setText(R.string.ready);
        downloadButton.setOnClickListener(downloadButtonClickListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkStatus.setNetworkAvailable(isNetworkAvailable());
        setupControls();
    }

    public  boolean isNetworkAvailable() {
            ConnectivityManager connMgr =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return  networkInfo != null && networkInfo.isConnected();
    }
}
