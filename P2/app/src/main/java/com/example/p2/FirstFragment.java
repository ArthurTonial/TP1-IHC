package com.example.p2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.p2.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment implements SensorEventListener {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SensorManager sensorManager = (SensorManager)requireActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        if (light != null) {
            sensorManager.registerListener(FirstFragment.this, light, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (pressure != null) {
            sensorManager.registerListener(FirstFragment.this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
        }

        ActivityCompat.requestPermissions(requireActivity(), new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION }, 123);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                GPSTracker gpsTracker = new GPSTracker(getContext());
                Location location = gpsTracker.getLocation();
                if (location != null) {
                    binding.lat.setText(String.format(Double.toString(location.getLatitude())));
                    binding.lon.setText(String.format(Double.toString(location.getLongitude())));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            binding.lightValue.setText("Light Intensity: " + Float.toString(sensorEvent.values[0]));
        }
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
            binding.psiValue.setText("Pressure: " + Float.toString(sensorEvent.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}