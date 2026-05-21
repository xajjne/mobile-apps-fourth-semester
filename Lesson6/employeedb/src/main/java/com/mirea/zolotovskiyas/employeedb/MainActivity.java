package com.mirea.zolotovskiyas.employeedb;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPower;
    private EditText editTextLevel;
    private Button buttonSave;
    private Button buttonShowAll;
    private TextView textViewResult;

    private SuperHeroDao superHeroDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextPower = findViewById(R.id.editTextPower);
        editTextLevel = findViewById(R.id.editTextLevel);
        buttonSave = findViewById(R.id.buttonSave);
        buttonShowAll = findViewById(R.id.buttonShowAll);
        textViewResult = findViewById(R.id.textViewResult);

        AppDatabase db = App.getInstance().getDatabase();
        superHeroDao = db.superHeroDao();

        buttonSave.setOnClickListener(v -> saveHero());
        buttonShowAll.setOnClickListener(v -> showAllHeroes());
    }

    private void saveHero() {
        String name = editTextName.getText().toString().trim();
        String power = editTextPower.getText().toString().trim();
        String levelString = editTextLevel.getText().toString().trim();

        if (name.isEmpty() || power.isEmpty() || levelString.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int level = Integer.parseInt(levelString);

        SuperHero superHero = new SuperHero();
        superHero.name = name;
        superHero.power = power;
        superHero.level = level;

        superHeroDao.insert(superHero);

        Toast.makeText(this, "Герой сохранён", Toast.LENGTH_SHORT).show();

        editTextName.setText("");
        editTextPower.setText("");
        editTextLevel.setText("");
    }

    private void showAllHeroes() {
        List<SuperHero> heroes = superHeroDao.getAll();

        if (heroes.isEmpty()) {
            textViewResult.setText("В базе пока нет героев");
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (SuperHero hero : heroes) {
            builder.append("ID: ").append(hero.id).append("\n");
            builder.append("Имя: ").append(hero.name).append("\n");
            builder.append("Сила: ").append(hero.power).append("\n");
            builder.append("Уровень: ").append(hero.level).append("\n\n");
        }

        textViewResult.setText(builder.toString());
    }
}