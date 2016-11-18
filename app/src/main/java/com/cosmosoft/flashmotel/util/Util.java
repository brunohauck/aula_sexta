package com.cosmosoft.flashmotel.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by p060783 on 16/12/2015.
 */
public class Util {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conectivtyManager.getActiveNetworkInfo() != null && conectivtyManager.getActiveNetworkInfo().isAvailable();
    }

}
