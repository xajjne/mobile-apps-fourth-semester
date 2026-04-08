package com.mirea.zolotovskiyas.workmanager;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.mirea.zolotovskiyas.workmanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonStartWork.setOnClickListener(v -> {
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED)

                    .build();

            OneTimeWorkRequest uploadWorkRequest =
                    new OneTimeWorkRequest.Builder(UploadWorker.class)
                            .setConstraints(constraints)
                            .build();

            WorkManager
                    .getInstance(this)
                    .enqueue(uploadWorkRequest);

            Toast.makeText(this,
                    "Worker поставлен в очередь",
                    Toast.LENGTH_SHORT).show();
        });
    }
}