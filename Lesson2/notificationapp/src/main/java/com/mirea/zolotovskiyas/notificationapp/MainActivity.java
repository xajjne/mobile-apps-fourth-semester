package com.mirea.zolotovskiyas.notificationapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "com.mirea.zolotovskiyas.notification.ANDROID";
    private static final String TAG = "NotificationApp";
    private static final int PERMISSION_CODE = 200;

    private static final String STUDENT_FIO = "Золотовский Александр Сергеевич";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNotificationPermission();
    }

    private void checkNotificationPermission() {
        // Для Android 13 (API 33) и выше нужно запрашивать разрешение
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Разрешения на уведомления получены");
                createNotificationChannel();
            } else {
                Log.d(TAG, "Нет разрешений на уведомления!");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        PERMISSION_CODE);
            }
        } else {
            Log.d(TAG, "Android версии ниже 13 - разрешение не требуется");
            createNotificationChannel();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Разрешение получено!");
                createNotificationChannel();
                Toast.makeText(this, "Разрешение получено! Теперь можно отправлять уведомления",
                        Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "Разрешение отклонено");
                Toast.makeText(this, "Без разрешения уведомления не будут показываться",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Student FIO Notification";
            String description = "MIREA Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            Log.d(TAG, "Канал уведомлений создан");
        }
    }

    public void onClickSendNotification(View view) {
        Log.d(TAG, "Кнопка нажата, отправляем уведомление");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Нет разрешения! Запрашиваем...");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        PERMISSION_CODE);
                return;
            }
        }

        sendNotification();
    }

    private void sendNotification() {
        String bigText = "Это уведомление создано студентом: " + STUDENT_FIO +
                "\n\nMuch longer text that cannot fit one line... " +
                "Здесь может быть очень длинный текст, который не помещается " +
                "в обычное уведомление и требует расширенного стиля.";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Mirea")
                .setContentText("Congratulations!")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(bigText))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        int notificationId = 1;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(notificationId, builder.build());

        Log.d(TAG, "Уведомление отправлено с ID: " + notificationId);
        Toast.makeText(this, "Уведомление отправлено!", Toast.LENGTH_SHORT).show();
    }
}