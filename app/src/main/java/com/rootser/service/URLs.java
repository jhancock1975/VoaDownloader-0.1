package com.rootser.service;

import com.rootser.URLsInf;

/**
 * Created by john on 5/3/14.
 * these should go in the local database
 */
public class URLs implements URLsInf {
    private  String VOA_6_7_MANDARIN="http://www.voanews.com/mp3/voa/eap/mand/mand2200a.mp3";
    public String[] getUrls() {
        return new String[] {VOA_6_7_MANDARIN};
    }
}
