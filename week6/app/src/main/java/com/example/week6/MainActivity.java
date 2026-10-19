package com.example.week6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("AdapterView");

        ArrayList<String> items = new ArrayList<>();
        items.add("CustomListView");
        items.add("RecyclerView");
        items.add("ExpandableListView");
        items.add("Spinner");
        items.add("CustomSpinner");
        items.add("GridView");
        items.add("CustomGrid");
        items.add("Gallery");
        items.add("ListActivity");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> handleItemClick(position));
    }

    private void handleItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, CustomListView.class);
                break;
            case 1:
                intent = new Intent(this, MyRecyclerView.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
