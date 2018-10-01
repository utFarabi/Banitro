package com.sm.banitro.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkReceiver extends BroadcastReceiver {

    // ********************************************************************************
    // Field

    // Instance
    public static Interaction interaction;

    // ********************************************************************************
    // Basic Override

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (isOnline(context) && interaction != null) {
            interaction.isConnecting();
        }
    }

    // ********************************************************************************
    // Method

    public boolean isOnline(Context context) {
        boolean isOnline = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            isOnline = (netInfo != null && netInfo.isConnected());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return isOnline;
    }

    // ********************************************************************************
    // Interface

    public interface Interaction {

        void isConnecting();
    }
}