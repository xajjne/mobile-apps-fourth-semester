package com.mirea.zolotovskiyas.lesson4;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.zolotovskiyas.lesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textSongTitle.setText("Believer");
        binding.textArtist.setText("Imagine Dragons");
        binding.textCurrentTime.setText("01:12");
        binding.textDuration.setText("03:24");
        binding.seekBarTrack.setProgress(35);

        binding.buttonPlay.setOnClickListener(v -> {
            isPlaying = !isPlaying;
            if (isPlaying) {
                binding.buttonPlay.setText("Pause");
                Toast.makeText(this, "Воспроизведение", Toast.LENGTH_SHORT).show();
            } else {
                binding.buttonPlay.setText("Play");
                Toast.makeText(this, "Пауза", Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonPrev.setOnClickListener(v ->
                Toast.makeText(this, "Предыдущий трек", Toast.LENGTH_SHORT).show()
        );

        binding.buttonNext.setOnClickListener(v ->
                Toast.makeText(this, "Следующий трек", Toast.LENGTH_SHORT).show()
        );
    }
}