package com.mirea.zolotovskiyas.mireaproject;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirea.zolotovskiyas.mireaproject.databinding.FragmentFilesBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FilesFragment extends Fragment {

    private FragmentFilesBinding binding;
    private ArrayAdapter<String> adapter;
    private final ArrayList<String> fileNames = new ArrayList<>();

    public FilesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFilesBinding.inflate(inflater, container, false);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, fileNames);
        binding.listViewFiles.setAdapter(adapter);

        refreshFiles();

        binding.fabAddFile.setOnClickListener(v -> showAddFileDialog());

        return binding.getRoot();
    }

    private void showAddFileDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_file, null);
        EditText editTextFileName = dialogView.findViewById(R.id.editTextFileName);
        EditText editTextFileContent = dialogView.findViewById(R.id.editTextFileContent);

        new AlertDialog.Builder(requireContext())
                .setTitle("Новая запись")
                .setView(dialogView)
                .setPositiveButton("Сохранить", (dialog, which) -> {
                    String fileName = editTextFileName.getText().toString().trim();
                    String content = editTextFileContent.getText().toString().trim();

                    if (TextUtils.isEmpty(fileName) || TextUtils.isEmpty(content)) {
                        Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    saveFile(fileName, content);
                    refreshFiles();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void saveFile(String fileName, String content) {
        FileOutputStream outputStream = null;
        try {
            outputStream = requireActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            Toast.makeText(requireContext(), "Файл сохранён", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private void refreshFiles() {
        fileNames.clear();

        File filesDir = requireContext().getFilesDir();
        File[] files = filesDir.listFiles();

        if (files != null) {
            for (File file : files) {
                fileNames.add(file.getName());
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}