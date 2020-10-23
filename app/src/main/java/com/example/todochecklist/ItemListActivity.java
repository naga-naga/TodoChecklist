package com.example.todochecklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}
