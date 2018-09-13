package com.sm.banitro.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    public Context context;
    private SharedPreferences pref;
    final String PREF_NAME = AppPreferences.class.getName();
    final String KEY_SELLER_ID = "sellerId";

    public AppPreferences(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public int getSellerId() {
        return pref.getInt(KEY_SELLER_ID, 0);
    }

    public void setSellerId(int sellerId) {
        pref.edit().putInt(KEY_SELLER_ID, sellerId).apply();
    }
}