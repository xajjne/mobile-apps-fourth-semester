package com.mirea.zolotovskiyas.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class DataFragment extends Fragment {

    public DataFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        Button buttonStartWorker = view.findViewById(R.id.buttonStartWorker);

        buttonStartWorker.setOnClickListener(v -> {
            OneTimeWorkRequest workRequest =
                    new OneTimeWorkRequest.Builder(MyWorker.class).build();

            WorkManager.getInstance(requireContext()).enqueue(workRequest);

            Toast.makeText(requireContext(),
                    "Фоновая задача запущена",
                    Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}