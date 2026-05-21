package com.mirea.zolotovskiyas.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirea.zolotovskiyas.mireaproject.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private static final String PREF_NAME = "profile_settings";
    private static final String KEY_NAME = "name";
    private static final String KEY_GROUP = "group";
    private static final String KEY_GENRE = "genre";

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        loadProfile();

        binding.buttonSaveProfile.setOnClickListener(v -> saveProfile());

        return binding.getRoot();
    }

    private void saveProfile() {
        String name = binding.editTextName.getText().toString().trim();
        String group = binding.editTextGroup.getText().toString().trim();
        String genre = binding.editTextGenre.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(group) || TextUtils.isEmpty(genre)) {
            Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = requireActivity()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_GROUP, group);
        editor.putString(KEY_GENRE, genre);
        editor.apply();

        Toast.makeText(requireContext(), "Профиль сохранён", Toast.LENGTH_SHORT).show();
    }

    private void loadProfile() {
        SharedPreferences sharedPreferences = requireActivity()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        binding.editTextName.setText(sharedPreferences.getString(KEY_NAME, ""));
        binding.editTextGroup.setText(sharedPreferences.getString(KEY_GROUP, ""));
        binding.editTextGenre.setText(sharedPreferences.getString(KEY_GENRE, ""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}