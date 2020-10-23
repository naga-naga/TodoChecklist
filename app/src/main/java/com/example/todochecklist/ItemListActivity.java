package com.example.todochecklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {
    private String checklist_name;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        Intent intent = getIntent();
        checklist_name = intent.getStringExtra(ChecklistLister.CHECKLIST_NAME);

        TextView textView = (TextView)findViewById(R.id.itemlist_checklist_name);
        textView.setText(checklist_name);

        // ---------- テスト用 ------------
        ListView listView = (ListView)findViewById(R.id.itemlist_list);
        ArrayList<Items> items = new ArrayList<>();
        CustomAdapter adapter = new CustomAdapter(this);

        adapter.setItems(items);
        listView.setAdapter(adapter);

        for(int i = 0; i < 5; i++){
            Items item = new Items();
            item.setCheckboxText("Checkbox" + (i+1));
            items.add(item);
        }
        adapter.notifyDataSetChanged();
    }
}
