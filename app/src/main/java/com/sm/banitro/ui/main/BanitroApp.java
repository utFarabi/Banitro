package com.sm.banitro.ui.main;

import android.app.Application;

import com.batch.android.Batch;
import com.batch.android.BatchActivityLifecycleHelper;
import com.batch.android.Config;

import java.io.File;

public class BanitroApp extends Application {

    // ********************************************************************************
    // Field

    // Instance
    private static BanitroApp banitroApp;

    // Data Type
    private static final String DEV_API_KEY = "DEV5BBCB8FE2C716C15EBA8A4C51A6";

    // ********************************************************************************
    // Basic Override

    @Override
    public void onCreate() {
        super.onCreate();
        banitroApp = this;
        Batch.setConfig(new Config(DEV_API_KEY));
        registerActivityLifecycleCallbacks(new BatchActivityLifecycleHelper());
    }

    // ********************************************************************************
    // Method

    public static BanitroApp getBanitroApp() {
        return banitroApp;
    }

    public void clearApplicationData() {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (int i = 0; i < fileNames.length; i++) {
                if (!fileNames[i].equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileNames[i]));
                }
            }
        }
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }
        return deletedAll;
    }
}