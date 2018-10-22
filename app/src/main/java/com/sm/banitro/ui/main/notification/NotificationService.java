package com.sm.banitro.ui.main.notification;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.sm.banitro.R;
import com.sm.banitro.util.NotificationID;
import com.sm.banitro.util.VersionUtil;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends IntentService {

    private static final String TAG = "NotificationService";
    private Timer timer;
    private TimerTask timerTask;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    public NotificationService() {
        super(NotificationService.class.getSimpleName());
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "run() called : notification api call");
                showNotification();
            }
        };
    }

    private void showNotification() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NotificationID.getID(), mBuilder.build());


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        timer.schedule(timerTask, 0, 3000);
//        timer.schedule(timerTask, 0, 3600 * 1000 * 4);
    }
}
