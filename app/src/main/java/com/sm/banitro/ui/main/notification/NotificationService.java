package com.sm.banitro.ui.main.notification;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends IntentService {

    private static final String TAG = "NotificationService";
    private Timer timer;
    private TimerTask timerTask;

    public NotificationService() {
        super(NotificationService.class.getSimpleName());
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "run() called : notification api call");
            }
        };
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        timer.schedule(timerTask, 0,3600*1000*4);
    }
}
