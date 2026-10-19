package com.example.myapp;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.RadioGroup;

public class MainActivity3 extends AppCompatActivity {
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main3);
        ScreenInsets.apply(findViewById(R.id.main));
        SeekBar celsius = findViewById(R.id.seekCelsius);
        SeekBar fahrenheit = findViewById(R.id.seekFahrenheit);
        TextView cText = findViewById(R.id.textCelsius);
        TextView fText = findViewById(R.id.textFahrenheit);
        TextView result = findViewById(R.id.textResult);
        RadioGroup direction = findViewById(R.id.groupDirection);
        celsius.setEnabled(false);
        fahrenheit.setEnabled(false);
        Runnable update = () -> {
            cText.setText(getString(R.string.celsius_value, celsius.getProgress() - 100));
            fText.setText(getString(R.string.fahrenheit_value, fahrenheit.getProgress() - 100));
            result.setText("");
        };
        SeekBarHelper.listen(celsius, update);
        SeekBarHelper.listen(fahrenheit, update);
        update.run();
        direction.setOnCheckedChangeListener((group, checkedId) -> {
            // 선택한 입력 방향의 SeekBar만 사용할 수 있게 합니다.
            celsius.setEnabled(checkedId == R.id.radioCelsius);
            fahrenheit.setEnabled(checkedId == R.id.radioFahrenheit);
            result.setText("");
        });
        findViewById(R.id.buttonConvert).setOnClickListener(v -> {
            if (direction.getCheckedRadioButtonId() == R.id.radioCelsius) {
                double value = celsius.getProgress() - 100;
                result.setText(getString(R.string.c_result, value, Calculations.toFahrenheit(value)));
            } else if (direction.getCheckedRadioButtonId() == R.id.radioFahrenheit) {
                double value = fahrenheit.getProgress() - 100;
                result.setText(getString(R.string.f_result, value, Calculations.toCelsius(value)));
            } else {
                Toast.makeText(this, R.string.choose_direction, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.buttonReset).setOnClickListener(v -> {
            direction.clearCheck();
            celsius.setProgress(100);
            fahrenheit.setProgress(100);
            update.run();
        });
    }
}
