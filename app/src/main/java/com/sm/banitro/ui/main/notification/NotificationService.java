package com.sm.banitro.ui.main.notification;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
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

    private static final String TAG = "NotificationService";
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private Timer timer;
    public static boolean isServiceRunning = false;
    private Repository repository;

    public NotificationService() {
        Log.d(TAG, "NotificationService() called");
        timer = new Timer();
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate() called");
        super.onCreate();
        repository = Repository.getINSTANCE(getApplicationContext());
        startNotificationService();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() called with: intent = [" + intent + "], flags = [" + flags + "], startId = [" + startId + "]");
        if (intent != null && intent.getAction().equals(ConstantUtil.ACTION_START_SERVICE)) {
            startNotificationService();
        } else {
            stopNotificationService();
        }
        return START_STICKY;

    }

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
        timer.schedule(timerTask, 0, 5000);// half of hour

    }

    private void makeApiCall() {
        repository.loadProductsForNotif(new ApiResult<ArrayList<Product>>() {
            @Override
            public void onSuccess(ArrayList<Product> result) {
                Log.d(TAG, "onSuccess() called with: result = [" + result.size() + "]");

                if (result.size() > 0) {
                    for (Product pr : result) {
                        Log.d(TAG, "onSuccess: " + pr.getReplyPrice());

                        Intent notificationIntent = new Intent(getApplicationContext(), NotificationService.class);
                        notificationIntent.setAction(Intent.ACTION_MAIN);
                        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 0, notificationIntent, 0);


                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(NotificationService.this, CHANNEL_ID)
                                .setSmallIcon(R.mipmap.ic_launcher_round)
                                .setContentTitle("شما یک محصول جدید دارید")
                                .setContentText(pr.getProName())
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                // Set the intent that will fire when the user taps the notification
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationService.this);


                        // Create an Intent for the activity you want to start
                        Intent resultIntent = new Intent(NotificationService.this, MainActivity.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotificationService.this);
                        stackBuilder.addNextIntentWithParentStack(resultIntent);
                        // Get the PendingIntent containing the entire back stack
                        PendingIntent resultPendingIntent =
                                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        mBuilder.setContentIntent(resultPendingIntent);
                        // notificationId is a unique int for each notification that you must define
                        notificationManager.notify(NotificationID.getID(), mBuilder.build());

                    }
                }
            }

            @Override
            public void onFail(String errorMessage) {
                Log.d(TAG, "onFail() called with: errorMessage = [" + errorMessage + "]");
            }
        });

    }

    private void stopNotificationService() {
        stopForeground(true);
        stopSelf();
        isServiceRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() called with: intent = [" + intent + "]");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        isServiceRunning = false;
        super.onDestroy();
    }
}
