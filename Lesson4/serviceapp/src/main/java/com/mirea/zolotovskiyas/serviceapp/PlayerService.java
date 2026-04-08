package com.mirea.zolotovskiyas.serviceapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class PlayerService extends Service {

    private MediaPlayer mediaPlayer;
    public static final String CHANNEL_ID = "PlayerServiceChannel";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Сектор газа - Пора домой")
                .setContentText("Идёт воспроизведение музыки")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        startForeground(1, notification);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }

        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(mp -> {
                stopForeground(true);
                stopSelf();
            });
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopForeground(true);

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Music Player Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            serviceChannel.setDescription("Канал для музыкального сервиса");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}