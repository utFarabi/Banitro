package com.sm.banitro.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    // ********************************************************************************
    // Field

    // Instance
    public Context context;
    private SharedPreferences pref;

    // Data Type
    final String PREF_NAME = AppPreferences.class.getName();
    final String KEY_FIRST_LOGIN = "firstLogin";
    final String KEY_SELLER_ID = "sellerId";

    // ********************************************************************************
    // Constructor

    public AppPreferences(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // ********************************************************************************
    // Method

    public boolean isFirstLogin() {
        return pref.getBoolean(KEY_FIRST_LOGIN, true);
    }

    public void setFirstLogin(boolean firstLogin) {
        pref.edit().putBoolean(KEY_FIRST_LOGIN, firstLogin).apply();
    }

    public String getSellerId() {
        return pref.getString(KEY_SELLER_ID, "407");
    }

    public void setSellerId(String sellerId) {
        pref.edit().putString(KEY_SELLER_ID, sellerId).apply();
    }
}