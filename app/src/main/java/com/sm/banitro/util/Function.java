package com.sm.banitro.util;

import android.content.Context;
import android.net.ConnectivityManager;

import com.sm.banitro.R;

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

    public static int getCategoryName(String id) {
        switch (id) {
            case Constant.CATEGORY_10:
                return R.string.category_10;
            case Constant.CATEGORY_12:
                return R.string.category_12;
            case Constant.CATEGORY_14:
                return R.string.category_14;
            case Constant.CATEGORY_16:
                return R.string.category_16;
            case Constant.CATEGORY_18:
                return R.string.category_18;
            case Constant.CATEGORY_20:
                return R.string.category_20;
            case Constant.CATEGORY_22:
                return R.string.category_22;
            case Constant.CATEGORY_24:
                return R.string.category_24;
            case Constant.CATEGORY_26:
                return R.string.category_26;
            case Constant.CATEGORY_28:
                return R.string.category_28;
            default:
                return 0;
        }
    }
}