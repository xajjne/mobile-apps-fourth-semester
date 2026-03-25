package com.mirea.zolotovskiyas.simplefragmentapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private Fragment firstFragment;
    private Fragment secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();

        if (findViewById(R.id.fragmentContainerView) != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            if (savedInstanceState == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, secondFragment)
                        .commit();
            }

            Button btnFirstFragment = findViewById(R.id.btnFirstFragment);
            Button btnSecondFragment = findViewById(R.id.btnSecondFragment);

            btnFirstFragment.setOnClickListener(v ->
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, firstFragment)
                            .commit()
            );

            btnSecondFragment.setOnClickListener(v ->
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, secondFragment)
                            .commit()
            );
        }
    }
}