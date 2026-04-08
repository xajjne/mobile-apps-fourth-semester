package com.mirea.zolotovskiyas.serviceapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mirea.zolotovskiyas.serviceapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int PERMISSION_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkNotificationPermission();

        binding.buttonStart.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(MainActivity.this, PlayerService.class);
            ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
            Toast.makeText(MainActivity.this, "Музыка запущена", Toast.LENGTH_SHORT).show();
        });

        binding.buttonStop.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(MainActivity.this, PlayerService.class);
            stopService(serviceIntent);
            Toast.makeText(MainActivity.this, "Музыка остановлена", Toast.LENGTH_SHORT).show();
        });
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        PERMISSION_CODE
                );
            }
        }
    }
}