package com.mirea.zolotovskiyas.cryptoloader;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.mirea.zolotovskiyas.cryptoloader.databinding.ActivityMainBinding;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private ActivityMainBinding binding;

    private static final int LOADER_ID = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phrase = binding.editPhrase.getText().toString().trim();

                if (phrase.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Введите фразу", Toast.LENGTH_SHORT).show();
                    return;
                }

                SecretKey key = generateKey();
                byte[] encryptedText = encryptMsg(phrase, key);

                Bundle bundle = new Bundle();
                bundle.putByteArray(MyLoader.ARG_CIPHER_TEXT, encryptedText);
                bundle.putByteArray(MyLoader.ARG_KEY, key.getEncoded());

                LoaderManager.getInstance(MainActivity.this).restartLoader(LOADER_ID, bundle, MainActivity.this);
            }
        });
    }

    public static SecretKey generateKey() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes(StandardCharsets.UTF_8));
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return new SecretKeySpec(kg.generateKey().getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encryptMsg(String message, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 java.security.InvalidKeyException |
                 javax.crypto.BadPaddingException |
                 javax.crypto.IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 javax.crypto.IllegalBlockSizeException |
                 javax.crypto.BadPaddingException |
                 java.security.InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == LOADER_ID) {
            Toast.makeText(this, "Loader запущен", Toast.LENGTH_SHORT).show();
            return new MyLoader(this, args);
        }
        throw new IllegalArgumentException("Invalid loader id");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Toast.makeText(this, "Расшифрованная фраза: " + data, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }
}