package com.example.myapplication;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.method.CharacterPickerDialog;
import android.widget.NumberPicker;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Dialog activeDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 시스템 바에 버튼이 가려지지 않도록 기존 여백에 더합니다.
        View main = findViewById(R.id.main);
        int padding = main.getPaddingLeft();
        ViewCompat.setOnApplyWindowInsetsListener(main, (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(padding + insets.left, padding + insets.top,
                    padding + insets.right, padding + insets.bottom);
            return windowInsets;
        });
        ViewCompat.requestApplyInsets(main);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1(v);

            }

            private void showDialog1(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("일반 다이얼로그");
                builder.setMessage("앱을 종료하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "확인했습니다", LENGTH_SHORT).show();

                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(v, "취소하였습니다", BaseTransientBottomBar.LENGTH_SHORT).show();

                    }
                });

                builder.setNeutralButton("중립", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "중립입니다", LENGTH_SHORT).show();

                    }
                });

                builder.setCancelable(false);
                builder.create();
                builder.show();
            }
        });



        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String temp = year + "년 ";
                        temp += (month + 1) + "월 ";
                        temp += dayOfMonth + "일 ";

                        Toast.makeText(getBaseContext(),temp, LENGTH_SHORT).show();
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, dateSetListener, year, month, day);
                dialog.create();
                dialog.show();

            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(Locale.KOREA);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(getBaseContext(), hourOfDay + " : " + minute, LENGTH_SHORT).show();
                    }
                };

                TimePickerDialog dialog = new TimePickerDialog(MainActivity.this, timeSetListener, hour, minute, false);
                dialog.create();
                dialog.show();

            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = Calendar.getInstance().get(Calendar.YEAR);
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_birthday);
                activeDialog = dialog;

                // 대화상자 안의 위젯은 dialog.findViewById()로 찾습니다.
                NumberPicker picker = dialog.findViewById(R.id.birthdayPicker);
                picker.setMinValue(year - 80);
                picker.setMaxValue(year - 15);
                picker.setValue(year - 20);
                picker.setWrapSelectorWheel(false);
                picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                Button button41 = dialog.findViewById(R.id.button41);
                button41.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button button42 = dialog.findViewById(R.id.button42);
                button42.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,
                                "선택한 출생 연도: " + picker.getValue() + "년", LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String options = "0321458769ACBFED";
                // 선택한 문자를 담을 공간과 삽입 위치를 준비합니다.
                SpannableStringBuilder selected = new SpannableStringBuilder();
                Selection.setSelection(selected, 0);
                CharacterPickerDialog dialog = new CharacterPickerDialog(
                        MainActivity.this, v, selected, options, true);
                activeDialog = dialog;
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (selected.length() > 0) {
                            Toast.makeText(MainActivity.this,
                                    "선택한 문자: " + selected, LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });

        Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
            }
        });
        Button button7 = findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHorizontalProgressDialog();
            }
        });

        Button button8 = findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog dialog = new CustomDialog(MainActivity.this);
                activeDialog = dialog;
                dialog.setCancelable(false);
                dialog.show();
            }
        });
    }

    // 강의의 ProgressDialog 실습입니다. 실제 다운로드가 아닌 5초 로딩 예제입니다.
    @SuppressWarnings("deprecation")
    private void showProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        activeDialog = dialog;
        dialog.setTitle("공지");
        dialog.setMessage("잠시만 기다려 주세요!\n프로그램 로딩 중입니다.");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "중지",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface window, int which) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "중지하였습니다", LENGTH_SHORT).show();
                    }
                });
        Runnable complete = new Runnable() {
            @Override
            public void run() {
                if (!isFinishing() && !isDestroyed() && dialog.isShowing()) {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "프로그램 로딩 완료", LENGTH_SHORT).show();
                }
            }
        };
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface window) {
                // 중지한 뒤에 완료 메시지가 나타나지 않도록 예약을 해제합니다.
                handler.removeCallbacks(complete);
            }
        });
        dialog.show();
        handler.postDelayed(complete, 5000);
    }

    // 실습용 로딩: 0~200까지 0.25초마다 10씩 증가합니다.
    @SuppressWarnings("deprecation")
    private void showHorizontalProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this,
                android.R.style.Theme_DeviceDefault_Light_Dialog);
        activeDialog = dialog;
        dialog.setTitle("막대형 로딩창");
        dialog.setMessage("로딩 중...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(200);
        dialog.setProgress(0);
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "중지",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface window, int which) {
                        dialog.dismiss();
                        Toast.makeText(getBaseContext(), "중지하였습니다", LENGTH_SHORT).show();
                    }
                });
        Runnable update = new Runnable() {
            @Override
            public void run() {
                if (isFinishing() || isDestroyed() || !dialog.isShowing()) return;
                dialog.incrementProgressBy(10);
                if (dialog.getProgress() >= dialog.getMax()) {
                    dialog.dismiss();
                    Toast.makeText(getBaseContext(), "로딩 완료", LENGTH_SHORT).show();
                } else {
                    handler.postDelayed(this, 250);
                }
            }
        };
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface window) {
                handler.removeCallbacks(update);
            }
        });
        dialog.show();
        dialog.getWindow().setDimAmount(0);
        handler.postDelayed(update, 250);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        if (activeDialog != null && activeDialog.isShowing()) {
            activeDialog.dismiss();
        }
        super.onDestroy();
    }
}
