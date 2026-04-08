package com.mirea.zolotovskiyas.data_thread;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.zolotovskiyas.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvInfo.setText("Старт программы...\n");

        final Runnable runn1 = new Runnable() {
            @Override
            public void run() {
                binding.tvInfo.append("1) runOnUiThread() выполнился\n");
                binding.tvInfo.append("   Этот метод сразу ставит Runnable в очередь UI-потока Activity\n\n");
            }
        };

        final Runnable runn2 = new Runnable() {
            @Override
            public void run() {
                binding.tvInfo.append("2) post() выполнился\n");
                binding.tvInfo.append("   Этот метод отправляет Runnable в очередь сообщений конкретного View\n\n");
            }
        };

        final Runnable runn3 = new Runnable() {
            @Override
            public void run() {
                binding.tvInfo.append("3) postDelayed() выполнился\n");
                binding.tvInfo.append("   Этот метод запускает Runnable в UI-потоке, но с задержкой\n\n");

                binding.tvInfo.append("Итог:\n");
                binding.tvInfo.append("- runOnUiThread() → выполнение через Activity в UI-потоке\n");
                binding.tvInfo.append("- post() → выполнение через View без задержки\n");
                binding.tvInfo.append("- postDelayed() → выполнение через View с задержкой\n");
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(runn1);

                    Thread.sleep(1000);
                    binding.tvInfo.post(runn2);

                    binding.tvInfo.postDelayed(runn3, 2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}