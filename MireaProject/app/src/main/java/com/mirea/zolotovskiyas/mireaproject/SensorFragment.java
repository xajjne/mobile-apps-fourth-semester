package com.mirea.zolotovskiyas.mireaproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirea.zolotovskiyas.mireaproject.databinding.FragmentSensorBinding;

public class SensorFragment extends Fragment implements SensorEventListener {

    private FragmentSensorBinding binding;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    private final float[] gravityData = new float[3];
    private final float[] geomagneticData = new float[3];

    public SensorFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSensorBinding.inflate(inflater, container, false);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
        if (magnetometer != null) {
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (binding == null) return;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, gravityData, 0, event.values.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, geomagneticData, 0, event.values.length);
        }

        float[] rotationMatrix = new float[9];
        float[] orientation = new float[3];

        boolean success = SensorManager.getRotationMatrix(rotationMatrix, null, gravityData, geomagneticData);
        if (success) {
            SensorManager.getOrientation(rotationMatrix, orientation);
            float azimuthInRadians = orientation[0];
            float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);
            if (azimuthInDegrees < 0) {
                azimuthInDegrees += 360;
            }

            binding.textAzimuth.setText("Азимут: " + Math.round(azimuthInDegrees) + "°");
            binding.textDirection.setText("Направление: " + getDirection(azimuthInDegrees));
        }
    }

    private String getDirection(float azimuth) {
        if (azimuth >= 337.5 || azimuth < 22.5) return "Север";
        if (azimuth < 67.5) return "Северо-восток";
        if (azimuth < 112.5) return "Восток";
        if (azimuth < 157.5) return "Юго-восток";
        if (azimuth < 202.5) return "Юг";
        if (azimuth < 247.5) return "Юго-запад";
        if (azimuth < 292.5) return "Запад";
        return "Северо-запад";
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}