package com.example.mariokarttournamentorganizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Prompt user to allow notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        //Run the notification every 30 seconds
        ScheduledExecutorService scheduledTask = Executors.newScheduledThreadPool(5);
        scheduledTask.scheduleAtFixedRate(new Runnable() {
            public void run() { createNotification(); }
        }, 0, 30, TimeUnit.SECONDS);
    }

    public void createNotification() {
        String channelID = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), channelID);
        builder.setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Hello World!")
                .setContentText("Hello World.")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_0_1){
            NotificationChannel channel = manager.getNotificationChannel(channelID);
            if (channel == null){
                int importance = NotificationManager.IMPORTANCE_HIGH;
                channel = new NotificationChannel(channelID, "Some Description", importance);
                channel.setLightColor(Color.GREEN);
                channel.enableVibration(true);
                manager.createNotificationChannel(channel);
            }
        }

        manager.notify(0, builder.build());

    }
}