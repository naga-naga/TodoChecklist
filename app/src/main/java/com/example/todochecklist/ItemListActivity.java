package com.example.todochecklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemListActivity extends AppCompatActivity {
    private String checklistName;
    private ItemLister itemLister;
    private ItemListsManager itemListsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        Intent intent = getIntent();
        checklistName = intent.getStringExtra(ChecklistLister.CHECKLIST_NAME);

        itemListsManager = new ItemListsManager(this);

        TextView textView = (TextView)findViewById(R.id.itemlist_checklist_name);
        textView.setText(checklistName);

        ListView listView = (ListView)findViewById(R.id.itemlist_list);
        itemLister = new ItemLister(this, listView, itemListsManager, checklistName);

        // ---------- テスト用 ------------
//        ListView listView = (ListView)findViewById(R.id.itemlist_list);
//        ArrayList<Items> items = new ArrayList<>();
//        CustomAdapter adapter = new CustomAdapter(this);
//
//        adapter.setItems(items);
//        listView.setAdapter(adapter);
//
//        for(int i = 0; i < 5; i++){
//            Items item = new Items();
//            item.setCheckboxText("Checkbox" + (i+1));
//            items.add(item);
//        }
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart(){
        super.onStart();
        itemLister.updateListView();
    }

    public void addItem(View view){
        itemLister.addItem(view);
    }

    public void deleteItem(View view){
        itemLister.deleteItem(view);
    }
}
