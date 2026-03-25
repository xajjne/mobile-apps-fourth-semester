package com.mirea.zolotovskiyas.sharer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    private TextView textViewShareData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        textViewShareData = findViewById(R.id.textViewShareData);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                textViewShareData.setText(sharedText != null ? sharedText : "Текст не получен");
            } else if (type.startsWith("image/")) {
                Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                textViewShareData.setText(imageUri != null ? imageUri.toString() : "Изображение не получено");
            } else {
                textViewShareData.setText("Неподдерживаемый тип данных");
            }
        } else {
            textViewShareData.setText("Нет входящих данных");
        }
    }
}