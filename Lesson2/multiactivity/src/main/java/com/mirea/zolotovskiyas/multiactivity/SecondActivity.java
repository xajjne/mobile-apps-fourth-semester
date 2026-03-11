package com.mirea.zolotovskiyas.multiactivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    private TextView textViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textViewData = findViewById(R.id.textViewData);

        String receivedText = getIntent().getStringExtra("key");

        if (receivedText != null) {
            textViewData.setText(receivedText);
            Log.i(TAG, "onCreate() - получены данные: " + receivedText);
        } else {
            textViewData.setText("Данные не получены");
            Log.i(TAG, "onCreate() - данные не получены");
        }

        Log.i(TAG, "onCreate() - SecondActivity создана");
    }

    // Переопределяем методы жизненного цикла
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() - SecondActivity становится видимой");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume() - SecondActivity готова к работе");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause() - SecondActivity приостановлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop() - SecondActivity остановлена");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart() - SecondActivity перезапускается");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() - SecondActivity уничтожена");
    }
}