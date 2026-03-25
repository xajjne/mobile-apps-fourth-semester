package com.mirea.zolotovskiyas.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView textViewDeveloperBook;
    private TextView textViewDeveloperQuote;
    private EditText editTextUserBook;
    private EditText editTextUserQuote;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textViewDeveloperBook = findViewById(R.id.textViewDeveloperBook);
        textViewDeveloperQuote = findViewById(R.id.textViewDeveloperQuote);
        editTextUserBook = findViewById(R.id.editTextUserBook);
        editTextUserQuote = findViewById(R.id.editTextUserQuote);
        buttonSend = findViewById(R.id.buttonSend);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String developerBook = extras.getString(MainActivity.BOOK_NAME_KEY);
            String developerQuote = extras.getString(MainActivity.QUOTES_KEY);

            textViewDeveloperBook.setText("Любимая книга разработчика: " + developerBook);
            textViewDeveloperQuote.setText("Цитата из книги: " + developerQuote);
        }

        buttonSend.setOnClickListener(v -> {
            String userBook = editTextUserBook.getText().toString().trim();
            String userQuote = editTextUserQuote.getText().toString().trim();

            String resultText = "Название Вашей любимой книги: " + userBook + ". Цитата: " + userQuote;

            Intent data = new Intent();
            data.putExtra(MainActivity.USER_MESSAGE, resultText);
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}