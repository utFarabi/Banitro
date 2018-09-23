package com.sm.banitro.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class Function {

    private Function() {
        // No Instance
    }

    // ********************************************************************************
    // Basic

    public static boolean isConnecting(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    // ********************************************************************************
    // Supplementary

    public static String convertIntToStrMoney(int intInput, boolean boolSign) {
        String strStock = "";
        String strInput = intInput + "";
        char charSign = '+';
        if (intInput < 0) {
            strInput = strInput.substring(1);
            charSign = '-';
        }
        while (strInput.length() > 3) {
            strStock = " , " + strInput.substring(strInput.length() - 3, strInput.length()) + strStock;
            strInput = strInput.substring(0, strInput.length() - 3);
        }
        strStock = strInput + strStock;
        if (boolSign) {
            strStock = charSign + " " + strStock;
        }
        return strStock;
    }
}