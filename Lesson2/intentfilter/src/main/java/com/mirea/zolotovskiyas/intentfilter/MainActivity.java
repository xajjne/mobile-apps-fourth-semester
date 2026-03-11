package com.mirea.zolotovskiyas.intentfilter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "IntentFilterDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate() - приложение запущено");
    }

    public void onClickOpenBrowser(View view) {
        Log.i(TAG, "onClickOpenBrowser() - попытка открыть браузер");

        Uri address = Uri.parse("https://www.mirea.ru/");

        Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);

        startActivity(openLinkIntent);

        Log.i(TAG, "onClickOpenBrowser() - браузер запущен");
    }

    public void onClickShare(View view) {
        Log.i(TAG, "onClickShare() - попытка поделиться данными");

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.setType("text/plain");

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "МИРЭА");

        shareIntent.putExtra(Intent.EXTRA_TEXT, "Золотовский Александр Сергеевич");

        startActivity(Intent.createChooser(shareIntent, "МОИ ФИО"));

        Log.i(TAG, "onClickShare() - диалог выбора открыт");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}