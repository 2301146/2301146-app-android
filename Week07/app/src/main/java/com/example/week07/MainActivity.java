package com.example.week07;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {
    static int current = Color.GREEN;
    static int[] color = {0, 255, 0};
    private ActionBar actionBar;
    private TextView textView;
    private EditText editText;
    private ImageView imageView;
    private Button button;
    private ToggleButton toggle;
    private PopupMenu popupMenu;
    private ListPopupWindow window;
    private ActionMode actionMode;
    private int type = 0;
    private int buttonTextColor = Color.BLACK;
    private int buttonBackgroundColor = Color.LTGRAY;
    private int sizeItem = R.id.itemsize3;
    private boolean flag = true;
    private boolean codeFlag = true;
    private final String[] colors = {
            "버튼 배경색 선택", "Red", "Green", "Blue", "Yellow", "Cyan", "Magenta"
    };
    private final int[] backgroundColors = {
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View root = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(root, (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        toggle = findViewById(R.id.toggle);
        CheckBox checkBox = findViewById(R.id.checkbox);

        if (savedInstanceState != null) {
            current = savedInstanceState.getInt("toolbarColor", Color.GREEN);
            color = new int[]{Color.red(current), Color.green(current), Color.blue(current)};
            codeFlag = savedInstanceState.getBoolean("xmlMenu", true);
            type = savedInstanceState.getInt("textColor");
            flag = savedInstanceState.getBoolean("originalImage", true);
            sizeItem = savedInstanceState.getInt("sizeItem", R.id.itemsize3);
            buttonTextColor = savedInstanceState.getInt("buttonTextColor", Color.BLACK);
            buttonBackgroundColor = savedInstanceState.getInt("buttonBackgroundColor", Color.LTGRAY);
            imageView.setRotation(savedInstanceState.getFloat("rotation"));
            float scale = savedInstanceState.getFloat("scale", 1f);
            imageView.setScaleX(scale);
            imageView.setScaleY(scale);
        }
        actionBar.setBackgroundDrawable(new ColorDrawable(current));
        updateTextView();
        textView.setTextColor(type == 1 ? Color.RED : type == 2 ? Color.BLUE : Color.BLACK);
        editText.setTextSize(sizeItem == R.id.itemsize1 ? 24 : sizeItem == R.id.itemsize2 ? 14 : 18);
        imageView.setImageResource(flag ? R.drawable.picture : R.drawable.an1);
        button.setTextColor(buttonTextColor);
        button.setBackgroundTintList(ColorStateList.valueOf(buttonBackgroundColor));

        WindowInsetsControllerCompat controller =
                new WindowInsetsControllerCompat(getWindow(), root);
        controller.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        checkBox.setChecked(savedInstanceState == null
                || savedInstanceState.getBoolean("statusVisible", true));
        checkBox.setOnCheckedChangeListener((view, checked) -> {
            checkBox.setText(checked ? "Status Bar 보이기" : "Status Bar 삭제");
            if (checked) {
                controller.show(WindowInsetsCompat.Type.statusBars());
            } else {
                controller.hide(WindowInsetsCompat.Type.statusBars());
            }
        });
        if (!checkBox.isChecked()) {
            checkBox.setText("Status Bar 삭제");
            controller.hide(WindowInsetsCompat.Type.statusBars());
        }
        toggle.setChecked(savedInstanceState == null
                || savedInstanceState.getBoolean("toolbarVisible", true));
        toggle.setOnCheckedChangeListener((view, checked) -> updateToolbar());
        updateToolbar();
        registerForContextMenu(textView);
        setupButtonMenus();
        imageView.setOnLongClickListener(view -> {
            if (actionMode == null) {
                actionMode = startSupportActionMode(callback);
                if (actionMode != null) {
                    actionBar.hide();
                    view.setSelected(true);
                }
            }
            return true;
        });
        ViewCompat.requestApplyInsets(root);
    }

    private void updateToolbar() {
        if (toggle.isChecked() && actionMode == null) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }

    private void updateTextView() {
        textView.setText("TextView의 ContextMenu 예제\n(롱 클릭시 메뉴 나타남)-"
                + (codeFlag ? "XML" : "JAVA"));
    }

    private void setupButtonMenus() {
        popupMenu = new PopupMenu(this, button);
        popupMenu.getMenuInflater().inflate(R.menu.buttonmenu, popupMenu.getMenu());
        int checkedId = buttonTextColor == Color.RED ? R.id.itemRed
                : buttonTextColor == Color.BLUE ? R.id.itemBlue : R.id.itemBlack;
        popupMenu.getMenu().findItem(checkedId).setChecked(true);
        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.itemBlack) {
                buttonTextColor = Color.BLACK;
            } else if (id == R.id.itemRed) {
                buttonTextColor = Color.RED;
            } else if (id == R.id.itemBlue) {
                buttonTextColor = Color.BLUE;
            } else {
                return false;
            }
            button.setTextColor(buttonTextColor);
            item.setChecked(true);
            return true;
        });
        window = new ListPopupWindow(this);
        window.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                220, getResources().getDisplayMetrics()));
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setAnchorView(button);
        window.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, colors) {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return position > 0;
            }
        });
        window.setModal(false);
        window.setOnItemClickListener((parent, view, position, id) -> {
            if (position > 0) {
                buttonBackgroundColor = backgroundColors[position - 1];
                button.setBackgroundTintList(ColorStateList.valueOf(buttonBackgroundColor));
                window.dismiss();
            }
        });
        button.setOnClickListener(view -> {
            if (window.isShowing()) {
                window.dismiss();
            } else {
                window.show();
            }
            Toast.makeText(this, "버튼을 클릭하였습니다", Toast.LENGTH_SHORT).show();
        });
        button.setOnLongClickListener(view -> {
            window.dismiss();
            popupMenu.show();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(codeFlag ? R.id.code1 : R.id.code2).setChecked(true);
        menu.findItem(sizeItem).setChecked(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            new ColorSetting(this, actionBar).setColor();
        } else if (id == R.id.code1 || id == R.id.code2) {
            codeFlag = id == R.id.code1;
            updateTextView();
            item.setChecked(true);
        } else if (id == R.id.itemsize1 || id == R.id.itemsize2 || id == R.id.itemsize3) {
            sizeItem = id;
            editText.setTextSize(id == R.id.itemsize1 ? 24 : id == R.id.itemsize2 ? 14 : 18);
            item.setChecked(true);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
            ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        if (view == textView) {
            if (codeFlag) {
                getMenuInflater().inflate(R.menu.textmenu, menu);
            } else {
                menu.setHeaderTitle("TextView 메뉴(Code)");
                menu.setHeaderIcon(R.drawable.logo);
                menu.add(0, 1, 0, "글자색 Black");
                menu.add(0, 2, 1, "글자색 Red");
                menu.add(0, 3, 2, "글자색 Blue");
                menu.setGroupCheckable(0, true, true);
            }
            int[] ids = codeFlag
                    ? new int[]{R.id.itemBlack, R.id.itemRed, R.id.itemBlue}
                    : new int[]{1, 2, 3};
            menu.findItem(ids[type]).setChecked(true);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemBlack || id == 1) {
            type = 0;
            textView.setTextColor(Color.BLACK);
        } else if (id == R.id.itemRed || id == 2) {
            type = 1;
            textView.setTextColor(Color.RED);
        } else if (id == R.id.itemBlue || id == 3) {
            type = 2;
            textView.setTextColor(Color.BLUE);
        } else {
            return super.onContextItemSelected(item);
        }
        item.setChecked(true);
        return true;
    }

    private final ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.imagemenu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.change) {
                flag = !flag;
                imageView.setImageResource(flag ? R.drawable.picture : R.drawable.an1);
            } else if (id == R.id.rotate) {
                imageView.setRotation(imageView.getRotation() + 45f);
            } else if (id == R.id.scale) {
                imageView.setScaleX(2f);
                imageView.setScaleY(2f);
            } else if (id == R.id.normal) {
                imageView.setScaleX(1f);
                imageView.setScaleY(1f);
                imageView.setRotation(0f);
            } else {
                return false;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            imageView.setSelected(false);
            updateToolbar();
        }
    };

    @Override
    protected void onSaveInstanceState(@NonNull Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt("toolbarColor", current);
        state.putBoolean("xmlMenu", codeFlag);
        state.putInt("textColor", type);
        state.putInt("buttonTextColor", buttonTextColor);
        state.putInt("buttonBackgroundColor", buttonBackgroundColor);
        state.putInt("sizeItem", sizeItem);
        state.putBoolean("originalImage", flag);
        state.putFloat("rotation", imageView.getRotation());
        state.putFloat("scale", imageView.getScaleX());
        state.putBoolean("toolbarVisible", toggle.isChecked());
        state.putBoolean("statusVisible", ((CheckBox) findViewById(R.id.checkbox)).isChecked());
    }

    @Override
    protected void onDestroy() {
        if (window != null) {
            window.dismiss();
        }
        if (popupMenu != null) {
            popupMenu.dismiss();
        }
        super.onDestroy();
    }
}
