package com.mirea.zolotovskiyas.securesharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    private EditText editTextPoet;
    private ImageView imageViewPoet;
    private Button buttonSetImage;
    private Button buttonSave;

    private SharedPreferences secureSharedPreferences;

    private static final String PREF_NAME = "secret_shared_prefs";
    private static final String KEY_POET = "secure_poet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPoet = findViewById(R.id.editTextPoet);
        imageViewPoet = findViewById(R.id.imageViewPoet);
        buttonSetImage = findViewById(R.id.buttonSetImage);
        buttonSave = findViewById(R.id.buttonSave);

        initSecurePreferences();
        loadData();

        buttonSetImage.setOnClickListener(v -> {
            imageViewPoet.setImageResource(R.drawable.poet);
        });

        buttonSave.setOnClickListener(v -> saveData());
    }

    private void initSecurePreferences() {
        try {
            KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
            String mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);

            secureSharedPreferences = EncryptedSharedPreferences.create(
                    PREF_NAME,
                    mainKeyAlias,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveData() {
        String poet = editTextPoet.getText().toString();

        secureSharedPreferences.edit()
                .putString(KEY_POET, poet)
                .apply();

        Toast.makeText(this, "Данные сохранены в зашифрованном виде", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        String poet = secureSharedPreferences.getString(KEY_POET, "");
        editTextPoet.setText(poet);
    }
}