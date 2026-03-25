package com.mirea.zolotovskiyas.systemintentsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonCall;
    private Button buttonBrowser;
    private Button buttonMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCall = findViewById(R.id.buttonCall);
        buttonBrowser = findViewById(R.id.buttonBrowser);
        buttonMaps = findViewById(R.id.buttonMaps);

        buttonCall.setOnClickListener(v -> onClickCall());
        buttonBrowser.setOnClickListener(v -> onClickOpenBrowser());
        buttonMaps.setOnClickListener(v -> onClickOpenMaps());
    }

    private void onClickCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:89811112233"));
        startActivity(intent);
    }

    private void onClickOpenBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://developer.android.com"));
        startActivity(intent);
    }

    private void onClickOpenMaps() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:55.749479,37.613944"));
        startActivity(intent);
    }
}