package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 두 번째 화면 표시
        setContentView(R.layout.activity_main2);

        // 상단 제목 설정
        setTitle(R.string.lesson_title);


        // 공지문이 길면 한 줄로 흘러가도록 설정
        TextView textView1 = findViewById(R.id.textView1);
        textView1.setSelected(true);


        // 나타나기 / 사라지기 반복 애니메이션
        Animation anim = new AlphaAnimation(0.0f, 1.0f);

        anim.setDuration(100);
        anim.setStartOffset(20);

        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);


        TextView textView2 = findViewById(R.id.textView2);
        textView2.startAnimation(anim);


        // 버튼 가져오기
        Button button = findViewById(R.id.button);


        // 버튼을 짧게 클릭했을 때
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(
                        MainActivity2.this,
                        R.string.lesson_button_toast,
                        Toast.LENGTH_LONG
                ).show();

            }
        });


        // 버튼을 길게 클릭했을 때
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(
                        MainActivity2.this,
                        "Long Click",
                        Toast.LENGTH_LONG
                ).show();

                return true;
            }
        });

    }


    // XML의 android:onClick="textViewClicked"와 연결
    public void textViewClicked(View view) {

        Toast.makeText(
                this,
                R.string.lesson_text_toast,
                Toast.LENGTH_LONG
        ).show();

    }
}