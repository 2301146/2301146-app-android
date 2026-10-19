package com.example.myapp;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main2);
        ScreenInsets.apply(findViewById(R.id.main));
        SeekBar weight = findViewById(R.id.seekWeight);
        SeekBar height = findViewById(R.id.seekHeight);
        TextView weightText = findViewById(R.id.textWeight);
        TextView heightText = findViewById(R.id.textHeight);
        TextView result = findViewById(R.id.textBmi);
        Runnable update = () -> {
            double kg = Calculations.weight(weight.getProgress());
            double cm = Calculations.height(height.getProgress());
            weightText.setText(getString(R.string.weight_value, kg));
            heightText.setText(getString(R.string.height_value, cm));
            double bmi = Calculations.bmi(kg, cm);
            result.setText(getString(R.string.bmi_result, bmi, Calculations.bmiCategory(bmi)));
        };
        weightText.setText(getString(R.string.weight_value, 35.0));
        heightText.setText(getString(R.string.height_value, 90.0));
        // 버튼 없이 슬라이더를 움직이는 즉시 결과를 갱신합니다.
        SeekBarHelper.listen(weight, update);
        SeekBarHelper.listen(height, update);
    }
}
