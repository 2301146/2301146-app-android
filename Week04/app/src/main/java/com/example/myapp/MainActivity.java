package com.example.myapp;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private boolean firstSelected, secondSelected;
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        ScreenInsets.apply(findViewById(R.id.main));
        SeekBar first = findViewById(R.id.seekFirst);
        SeekBar second = findViewById(R.id.seekSecond);
        TextView firstText = findViewById(R.id.textFirst);
        TextView secondText = findViewById(R.id.textSecond);
        TextView result = findViewById(R.id.textResult);
        if (state != null) {
            first.setProgress(state.getInt("first"));
            second.setProgress(state.getInt("second"));
            firstSelected = state.getBoolean("firstSelected");
            secondSelected = state.getBoolean("secondSelected");
        }
        firstText.setText(getString(R.string.natural, first.getProgress() + 1));
        secondText.setText(getString(R.string.natural, second.getProgress() + 1));
        SeekBarHelper.listen(first, () -> {
            firstSelected = true;
            firstText.setText(getString(R.string.natural, first.getProgress() + 1));
            result.setText(R.string.result);
        });
        SeekBarHelper.listen(second, () -> {
            secondSelected = true;
            secondText.setText(getString(R.string.natural, second.getProgress() + 1));
            result.setText(R.string.result);
        });
        findViewById(R.id.buttonCalculate).setOnClickListener(v -> {
            if (!firstSelected || !secondSelected) {
                Toast.makeText(this, R.string.input_required, Toast.LENGTH_SHORT).show();
                return;
            }
            // progress 0~99를 자연수 1~100으로 바꿉니다.
            int a = first.getProgress() + 1;
            int b = second.getProgress() + 1;
            result.setText(getString(R.string.gcd_lcm_result, a, b,
                    Calculations.gcd(a, b), Calculations.lcm(a, b)));
        });
    }
    @Override protected void onSaveInstanceState(Bundle out) {
        out.putInt("first", ((SeekBar)findViewById(R.id.seekFirst)).getProgress());
        out.putInt("second", ((SeekBar)findViewById(R.id.seekSecond)).getProgress());
        out.putBoolean("firstSelected", firstSelected);
        out.putBoolean("secondSelected", secondSelected);
        super.onSaveInstanceState(out);
    }
}
