package com.mirea.zolotovskiyas.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);
    }

    public void onClickShowDialog(View view) {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
        textViewResult.setText("Диалог открыт");
    }

    public void onClickShowTimePicker(View view) {
        MyTimeDialogFragment timeDialogFragment = new MyTimeDialogFragment();
        timeDialogFragment.show(getSupportFragmentManager(), "timePicker");
        textViewResult.setText("Выберите время");
    }

    public void onClickShowDatePicker(View view) {
        MyDateDialogFragment dateDialogFragment = new MyDateDialogFragment();
        dateDialogFragment.show(getSupportFragmentManager(), "datePicker");
        textViewResult.setText("Выберите дату");
    }

    public void onClickShowProgress(View view) {
        MyProgressDialogFragment progressDialogFragment = new MyProgressDialogFragment();
        progressDialogFragment.show(getSupportFragmentManager(), "progressDialog");
        textViewResult.setText("Загрузка начата");
    }

    public void onOkClicked() {
        Toast.makeText(this, "Вы выбрали кнопку \"Иду дальше\"", Toast.LENGTH_LONG).show();
        textViewResult.setText("Выбрано: Иду дальше");
    }

    public void onNeutralClicked() {
        Toast.makeText(this, "Вы выбрали кнопку \"На паузе\"", Toast.LENGTH_LONG).show();
        textViewResult.setText("Выбрано: На паузе");
    }

    public void onCancelClicked() {
        Toast.makeText(this, "Вы выбрали кнопку \"Нет\"", Toast.LENGTH_LONG).show();
        textViewResult.setText("Выбрано: Нет");
    }

    public void onTimeSet(int hourOfDay, int minute) {
        String time = String.format("%02d:%02d", hourOfDay, minute);
        Toast.makeText(this, "Выбрано время: " + time, Toast.LENGTH_LONG).show();
        textViewResult.setText("Выбрано время: " + time);
    }

    public void onDateSet(int year, int month, int dayOfMonth) {
        String date = String.format("%02d.%02d.%d", dayOfMonth, month + 1, year);
        Toast.makeText(this, "Выбрана дата: " + date, Toast.LENGTH_LONG).show();
        textViewResult.setText("Выбрана дата: " + date);
    }

    public void onProgressFinished() {
        Toast.makeText(this, "Загрузка завершена!", Toast.LENGTH_LONG).show();
        textViewResult.setText("Загрузка завершена");
    }
}