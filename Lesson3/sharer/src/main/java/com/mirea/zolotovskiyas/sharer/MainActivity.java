package com.mirea.zolotovskiyas.sharer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonSend;
    private Button buttonPick;
    private TextView textViewResult;
    private ActivityResultLauncher<Intent> pickLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSend = findViewById(R.id.buttonSend);
        buttonPick = findViewById(R.id.buttonPick);
        textViewResult = findViewById(R.id.textViewResult);

        pickLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri = data != null ? data.getData() : null;
                        textViewResult.setText("Data: " + uri);
                    } else {
                        textViewResult.setText("Данные не выбраны");
                    }
                }
        );

        buttonSend.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Mirea");
            startActivity(Intent.createChooser(intent, "Выбор за вами!"));
        });

        buttonPick.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("*/*");
            pickLauncher.launch(intent);
        });
    }
}