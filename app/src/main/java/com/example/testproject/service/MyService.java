package com.example.testproject.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import com.example.testproject.R;
import com.example.testproject.activity.MainActivity;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    Integer data;
    boolean status;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        status = intent.getBooleanExtra("status", true);

        if(status){
            data = intent.getIntExtra("data", 1);
            sendNotification();
        } else {
            data = intent.getIntExtra("count", 1);
            deleteNotification();
        }


        return super.onStartCommand(intent, flags, startId);
    }

    private void deleteNotification() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification() {


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "TestChannel", NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("Это не очень важно");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);
        }

        Intent resultIntent = new Intent(this, MainActivity.class);

        resultIntent.putExtra("id", data );
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "1").setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getResources().getString(R.string.createNotif)).setContentText("notification " + data)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        Notification notification = builder.build();

        manager.notify(data, notification);





    }
}