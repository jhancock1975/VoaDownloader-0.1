package com.rootser;

import android.view.View;
import android.widget.TextView;

import com.google.inject.Inject;
import com.rootser.service.DownloadWebPageTask;

import roboguice.inject.InjectView;

/**
 * Created by john on 4/27/14.
 */
public class DownloadButtonClickListener  implements View.OnClickListener {
    @InjectView(R.id.downloadStatusText)
    private TextView textView;
    @Inject
    private DownloadWebPageTask downloadTask;
    @Inject
    private NetworkStatus networkStatus;
    @Override
    public void onClick(View view) {
        if (networkStatus.isNetworkAvailable()){
            textView.setText(R.string.downloading);
            downloadTask
                    .execute("http://www.voanews.com/mp3/voa/eap/mand/mand2200a.mp3");
        } else {
            textView.setText(R.string.network_not_available);
        }
    }
}
