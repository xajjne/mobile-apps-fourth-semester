package com.mirea.zolotovskiyas.lesson6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextGroup;
    private EditText editTextNumber;
    private EditText editTextFilm;
    private Button buttonSave;

    private static final String PREF_NAME = "mirea_settings";
    private static final String KEY_GROUP = "GROUP";
    private static final String KEY_NUMBER = "NUMBER";
    private static final String KEY_FILM = "FILM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextGroup = findViewById(R.id.editTextGroup);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextFilm = findViewById(R.id.editTextFilm);
        buttonSave = findViewById(R.id.buttonSave);

        loadData();

        buttonSave.setOnClickListener(v -> saveData());
    }

    private void saveData() {
        String group = editTextGroup.getText().toString();
        String numberString = editTextNumber.getText().toString();
        String film = editTextFilm.getText().toString();

        int number = 0;
        if (!numberString.isEmpty()) {
            number = Integer.parseInt(numberString);
        }

        SharedPreferences sharedPreferences =
                getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_GROUP, group);
        editor.putInt(KEY_NUMBER, number);
        editor.putString(KEY_FILM, film);
        editor.apply();

        Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        SharedPreferences sharedPreferences =
                getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String group = sharedPreferences.getString(KEY_GROUP, "");
        int number = sharedPreferences.getInt(KEY_NUMBER, 0);
        String film = sharedPreferences.getString(KEY_FILM, "");

        editTextGroup.setText(group);

        if (number != 0) {
            editTextNumber.setText(String.valueOf(number));
        }

        editTextFilm.setText(film);
    }
}