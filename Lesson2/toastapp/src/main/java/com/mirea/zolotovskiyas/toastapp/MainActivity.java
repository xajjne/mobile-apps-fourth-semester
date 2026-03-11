package com.mirea.zolotovskiyas.toastapp;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInput;

    private static final String STUDENT_NAME = "Золотовский А.С."; // Твое ФИО
    private static final String GROUP = "БСБО-50-24";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.editTextInput);
    }

    public void onClickCount(View view) {
        String inputText = editTextInput.getText().toString();

        int charCount = inputText.length();

        String message = "СТУДЕНТ № " + STUDENT_NAME +
                " ГРУППА " + GROUP +
                " Кол-во сим. - " + charCount;

        Toast defaultToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        defaultToast.show();

        createCustomToast(message);
    }

    private void createCustomToast(String message) {
        Toast customToast = Toast.makeText(this, message, Toast.LENGTH_LONG);

        customToast.setGravity(Gravity.CENTER, 0, 0);

        customToast.show();
    }
}