package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Activity의 기본 초기화
        super.onCreate(savedInstanceState);

        // activity_main.xml을 화면에 표시
        setContentView(R.layout.activity_main);

        // 상단 바의 제목 설정
        setTitle(R.string.app_name);

        // 상단 바에 아이콘 표시
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setLogo(R.mipmap.ic_launcher);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        // 화면의 글자 찾기
        TextView textView = findViewById(R.id.textView);

        // 글자를 누르면 내용 변경
        textView.setOnClickListener(v -> {
            textView.setText("눌렀네요!");
        });
    }
}