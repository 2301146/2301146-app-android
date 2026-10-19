package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class MainActivity extends BaseActivity {
    private EditText gradeField;
    private final ActivityResultLauncher<Intent> picker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    gradeField.setText(result.getData().getStringExtra("grade"));
                }
            });
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        setup(R.layout.activity_main);
        gradeField = findViewById(R.id.gradeField);
        gradeField.setOnClickListener(v -> picker.launch(new Intent(this, GradeActivity.class)));
    }
}
