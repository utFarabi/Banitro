package com.sm.banitro.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class Permission {

    private Permission() {
        // No Instance
    }

    // ********************************************************************************
    // Data Type

    // Image
    public static final int IMAGE_PERMISSION_REQUEST_CODE = 102;
    public static final String[] IMAGE_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    // ********************************************************************************
    // Method

    // Camera
    public static boolean iscameraPermissionGranted(Context context) {
        return ActivityCompat.
                checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    // Storage
    public static boolean isStoragePermissionGranted(Context context) {
        return ActivityCompat
                .checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.
                        checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
}