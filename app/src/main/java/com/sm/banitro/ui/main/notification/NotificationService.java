package com.sm.banitro.ui.main.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.sm.banitro.R;
import com.sm.banitro.data.model.product.Product;
import com.sm.banitro.data.source.remote.ApiResult;
import com.sm.banitro.data.source.remote.Repository;
import com.sm.banitro.ui.main.MainActivity;
import com.sm.banitro.util.ConstantUtil;
import com.sm.banitro.util.NotificationID;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    // ********************************************************************************
    // Field

    // Instance
    private Repository repository;
    private Timer timer;

    // Data Type
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final int KEY_PERIOD = 300000; // 5 min
    public static boolean isServiceRunning = false;

    // ********************************************************************************
    // Basic Override

    @Override
    public void onCreate() {
        super.onCreate();
        repository = Repository.newInstance(getApplicationContext());
        timer = new Timer();
        startNotificationService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction().equals(ConstantUtil.ACTION_START_SERVICE)) {
            startNotificationService();
        } else {
            stopNotificationService();
        }
        return START_STICKY;
    }

    // ********************************************************************************
    // Method

    private void startNotificationService() {
        if (isServiceRunning) {
            return;
        }
        isServiceRunning = true;

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                makeApiCall();
            }
        };
        timer.schedule(timerTask, 0, KEY_PERIOD);
    }

    private void makeApiCall() {
        repository.loadProductsForNotif(new ApiResult<ArrayList<Product>>() {

            @Override
            public void onSuccess(ArrayList<Product> result) {
                Log.i("sina", "onSuccess: "+result.size());
                if (result.size() > 0) {
                    for (Product pr : result) {
                        Intent notificationIntent = new Intent(getApplicationContext(), NotificationService.class);
                        notificationIntent.setAction(Intent.ACTION_MAIN);
                        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 0, notificationIntent, 0);

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(NotificationService.this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.outline_chat_bubble_outline_black_36)
                                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher_round))
                                .setContentTitle(getString(R.string.new_product))
                                .setContentText("نام:  " + pr.getProName())
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setDefaults(Notification.DEFAULT_SOUND)
                                .setAutoCancel(true);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationService.this);

                        Intent resultIntent = new Intent(NotificationService.this, MainActivity.class);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotificationService.this);
                        stackBuilder.addNextIntentWithParentStack(resultIntent);
                        PendingIntent resultPendingIntent =
                                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        mBuilder.setContentIntent(resultPendingIntent);
                        notificationManager.notify(NotificationID.getID(), mBuilder.build());
                    }
                }
            }

            @Override
            public void onFail(String errorMessage) {
            }
        });
    }

    private void stopNotificationService() {
        stopForeground(true);
        stopSelf();
        isServiceRunning = false;
    }

    // ********************************************************************************
    // Supplementary Override

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        isServiceRunning = false;
        super.onDestroy();
    }
}