package com.mirea.zolotovskiyas.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.zolotovskiyas.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread mainThread = Thread.currentThread();
        binding.textThreadInfo.setText(
                "Текущий поток: " + mainThread.getName() + "\n" +
                        "Приоритет: " + mainThread.getPriority() + "\n" +
                        "Группа: " + mainThread.getThreadGroup()
        );

        mainThread.setName("MireaThread");

        binding.textThreadInfo.append("\nНовое имя потока: " + mainThread.getName());

        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String totalClassesStr = binding.editTotalClasses.getText().toString().trim();
                String studyDaysStr = binding.editStudyDays.getText().toString().trim();

                if (totalClassesStr.isEmpty() || studyDaysStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                int totalClasses = Integer.parseInt(totalClassesStr);
                int studyDays = Integer.parseInt(studyDaysStr);

                if (studyDays == 0) {
                    Toast.makeText(MainActivity.this, "Количество учебных дней не может быть 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int numberThread = ++counter;

                        Log.d("ThreadProject", "Запущен поток № " + numberThread);

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        final double result = (double) totalClasses / studyDays;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.textResult.setText(
                                        "Среднее количество пар в день: " + result
                                );
                            }
                        });

                        Log.d("ThreadProject", "Выполнен поток № " + numberThread);
                    }
                }).start();
            }
        });
    }
}