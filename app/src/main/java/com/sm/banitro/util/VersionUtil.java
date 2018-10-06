package com.sm.banitro.util;

import android.os.Build;

public class VersionUtil {

    private VersionUtil() {
        // No Instance
    }

    // ********************************************************************************
    // API

    // 19
    public static boolean isVersionCodesKitkatSupported() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    // 23
    public static boolean isVersionCodesMSupported() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}