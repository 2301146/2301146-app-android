package com.example.sample;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 상태 표시줄과 내비게이션 영역에 내용이 겹치지 않도록 여백 설정
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        // XML에 배치한 이미지와 버튼 찾기
        ImageView imageView = findViewById(R.id.pinwheel);
        Button startButton = findViewById(R.id.start);
        Button stopButton = findViewById(R.id.stop);

        // 0도부터 360도까지 회전하도록 설정
        animator = ObjectAnimator.ofFloat(
                imageView, "rotation", 0f, 360f
        );

        // 일정한 속도로 회전
        animator.setInterpolator(new LinearInterpolator());

        // 한 바퀴에 2초
        animator.setDuration(2000);

        // 무한 반복
        animator.setRepeatCount(ValueAnimator.INFINITE);

        // 앱을 열면 자동으로 회전
        animator.start();

        // 시작 버튼을 눌렀을 때
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!animator.isRunning()) {
                    animator.start();
                }
            }
        });

        // 정지 버튼을 눌렀을 때
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animator.cancel();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (animator != null) {
            animator.cancel();
        }

        super.onDestroy();
    }
}