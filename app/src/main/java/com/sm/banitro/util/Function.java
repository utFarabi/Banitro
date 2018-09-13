package com.sm.banitro.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class Function {

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager =
                ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}