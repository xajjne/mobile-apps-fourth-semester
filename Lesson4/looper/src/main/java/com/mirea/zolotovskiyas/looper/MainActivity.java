package com.mirea.zolotovskiyas.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.zolotovskiyas.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.getData().getString("RESULT");
                Log.d(MainActivity.class.getSimpleName(),
                        "Результат из фонового потока: " + result);
            }
        };

        myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = binding.editAge.getText().toString().trim();
                String job = binding.editJob.getText().toString().trim();

                if (age.isEmpty() || job.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Заполните все поля",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (myLooper.mHandler == null) {
                    Toast.makeText(MainActivity.this,
                            "Поток еще не подготовлен, нажмите чуть позже",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("AGE", age);
                bundle.putString("JOB", job);
                msg.setData(bundle);

                myLooper.mHandler.sendMessage(msg);

                Log.d(MainActivity.class.getSimpleName(),
                        "Сообщение отправлено в MyLooper");
            }
        });
    }
}