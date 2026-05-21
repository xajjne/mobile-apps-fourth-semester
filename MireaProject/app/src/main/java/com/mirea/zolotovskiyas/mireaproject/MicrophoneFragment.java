package com.mirea.zolotovskiyas.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.mirea.zolotovskiyas.mireaproject.databinding.FragmentMicrophoneBinding;

import java.io.File;
import java.io.IOException;

public class MicrophoneFragment extends Fragment {

    private FragmentMicrophoneBinding binding;
    private MediaRecorder recorder;
    private MediaPlayer player;
    private boolean isRecording = false;
    private String recordFilePath;

    private final ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    Toast.makeText(requireContext(), "Разрешение на микрофон не получено", Toast.LENGTH_SHORT).show();
                }
            });

    public MicrophoneFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMicrophoneBinding.inflate(inflater, container, false);

        recordFilePath = new File(
                requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "mirea_note.3gp"
        ).getAbsolutePath();

        binding.buttonRecord.setOnClickListener(v -> {
            if (!hasAudioPermission()) {
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
                return;
            }

            if (!isRecording) {
                startRecording();
            } else {
                stopRecording();
            }
        });

        binding.buttonPlay.setOnClickListener(v -> playRecord());

        return binding.getRoot();
    }

    private boolean hasAudioPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void startRecording() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                recorder = new MediaRecorder(requireContext());
            } else {
                recorder = new MediaRecorder();
            }

            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(recordFilePath);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.prepare();
            recorder.start();

            isRecording = true;
            binding.buttonRecord.setText("Остановить запись");
            binding.textViewStatus.setText("Статус: идёт запись");
        } catch (IOException e) {
            binding.textViewStatus.setText("Статус: ошибка записи");
        }
    }

    private void stopRecording() {
        try {
            recorder.stop();
            recorder.release();
            recorder = null;

            isRecording = false;
            binding.buttonRecord.setText("Начать запись");
            binding.textViewStatus.setText("Статус: запись сохранена");
        } catch (Exception e) {
            binding.textViewStatus.setText("Статус: ошибка остановки");
        }
    }

    private void playRecord() {
        try {
            if (player != null) {
                player.release();
            }

            player = new MediaPlayer();
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();

            binding.textViewStatus.setText("Статус: воспроизведение");
            player.setOnCompletionListener(mp -> binding.textViewStatus.setText("Статус: воспроизведение завершено"));
        } catch (IOException e) {
            Toast.makeText(requireContext(), "Нет записи для воспроизведения", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}