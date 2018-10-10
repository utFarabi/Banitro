package com.sm.banitro.ui.main;

import android.app.Application;

import com.batch.android.Batch;
import com.batch.android.BatchActivityLifecycleHelper;
import com.batch.android.Config;

public class BanitroApp extends Application {

    private static final String DEV_API_KEY = "DEV5BBCB8FE2C716C15EBA8A4C51A6";

    @Override
    public void onCreate() {
        super.onCreate();
        Batch.setConfig(new Config(DEV_API_KEY));
        registerActivityLifecycleCallbacks(new BatchActivityLifecycleHelper());
    }
}
