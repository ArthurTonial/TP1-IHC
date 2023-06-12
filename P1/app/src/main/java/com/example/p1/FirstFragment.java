package com.example.p1;

import static java.lang.Math.abs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.p1.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment implements SensorEventListener {

    private FragmentFirstBinding binding;
    private boolean flag = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.sumButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String str_num1 = binding.num1.getText().toString();
                String str_num2 = binding.num2.getText().toString();
                int int_result = Integer.parseInt(str_num1) + Integer.parseInt(str_num2);
                binding.result.setText("Answer = " + int_result);
            }
        });
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("text", binding.text.getText().toString());
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
            }
        });
        SensorManager sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(FirstFragment.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (flag)
            return;


        if (abs(event.values[0]) > 9.5 || event.values[1] < 9.5 || abs(event.values[2]) > 9.5) {
            flag = true;

            Bundle bundle = new Bundle();
            bundle.putString("X", Float.toString(event.values[0]));
            bundle.putString("Y", Float.toString(event.values[1]));
            bundle.putString("Z", Float.toString(event.values[2]));

            binding.xAcc.setText(String.format(Float.toString(event.values[0])));
            binding.yAcc.setText(String.format(Float.toString(event.values[1])));
            binding.zAcc.setText(String.format(Float.toString(event.values[2])));

            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_ThirdFragment, bundle);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}