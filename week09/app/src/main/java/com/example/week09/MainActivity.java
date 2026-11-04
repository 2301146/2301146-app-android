package com.example.week09;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 버튼에 따라 Fragment 또는 DialogFragment 예제 화면으로 이동합니다.
        Intent intent;
        if (view.getId() == R.id.button1) {
            intent = new Intent(this, FragmentActivity.class);
        } else {
            intent = new Intent(this, DialogFragmentActivity.class);
        }
        startActivity(intent);
    }
}
