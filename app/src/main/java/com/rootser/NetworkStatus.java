package com.rootser;

import com.google.inject.Singleton;

/**
 * Created by john on 5/5/14.
 */
@Singleton
public class NetworkStatus {
    Boolean networkAvailable;
    public NetworkStatus(){
        networkAvailable = false;
    }
    public Boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public void setNetworkAvailable(Boolean networkAvailable) {
        this.networkAvailable = networkAvailable;
    }
}
