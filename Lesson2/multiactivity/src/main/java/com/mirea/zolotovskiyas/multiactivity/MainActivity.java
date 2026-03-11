package com.mirea.zolotovskiyas.multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        Log.i(TAG, "onCreate() - MainActivity создана");
    }

    // Обработчик для кнопки "Start new activity!"
    public void onClickNewActivity(View view) {
        Log.i(TAG, "onClickNewActivity() - запуск SecondActivity");

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    // Обработчик для кнопки "Отправить"
    public void onClickSend(View view) {
        Log.i(TAG, "onClickSend() - отправка данных во SecondActivity");

        String textToSend = editText.getText().toString();

        // Если поле пустое, отправляем сообщение по умолчанию
        if (textToSend.isEmpty()) {
            textToSend = "MIREA - ФАМИЛИЯ ИМЯ ОТЧЕСТВО СТУДЕНТА";
        }

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("key", textToSend);
        startActivity(intent);
    }

    // Переопределяем методы жизненного цикла
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() - MainActivity становится видимой");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume() - MainActivity готова к работе");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause() - MainActivity приостановлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop() - MainActivity остановлена");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart() - MainActivity перезапускается");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() - MainActivity уничтожена");
    }
}