package ru.mirea.zolotovskiyas.lesson2;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityLifecycle extends AppCompatActivity {
    private static final String TAG = "LifecycleDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate() - активность создана");

        if (savedInstanceState != null) {
            Log.i(TAG, "onCreate() - есть сохраненное состояние");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() - активность становится видимой");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume() - активность готова к взаимодействию");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause() - активность приостановлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop() - активность остановлена");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart() - перезапуск активности");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() - активность уничтожена");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState() - сохранение состояния");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState() - восстановление состояния");
    }
}