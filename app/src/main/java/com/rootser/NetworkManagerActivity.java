package com.rootser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import roboguice.activity.RoboActivity;

/**
 * Created by john on 4/30/14.
 */
public class NetworkManagerActivity extends RoboActivity {
    private Boolean networkAvailable = null;


    public Boolean isNetworkAvailable() {
        if (networkAvailable == null) {
            ConnectivityManager connMgr =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            networkAvailable = networkInfo != null && networkInfo.isConnected();
        }
        return networkAvailable;
    }

    public Boolean retryNetwork() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        networkAvailable = networkInfo != null && networkInfo.isConnected();
        return networkAvailable;
    }
}
