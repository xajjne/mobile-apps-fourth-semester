package com.mirea.zolotovskiyas.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {

    public static final String ARG_CIPHER_TEXT = "cipher_text";
    public static final String ARG_KEY = "key";

    private final Bundle args;

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        this.args = args;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        if (args == null) {
            return "Ошибка: Bundle пустой";
        }

        byte[] cipherText = args.getByteArray(ARG_CIPHER_TEXT);
        byte[] keyBytes = args.getByteArray(ARG_KEY);

        if (cipherText == null || keyBytes == null) {
            return "Ошибка: не переданы данные для расшифровки";
        }

        SystemClock.sleep(2000);

        SecretKey originalKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
        return MainActivity.decryptMsg(cipherText, originalKey);
    }
}