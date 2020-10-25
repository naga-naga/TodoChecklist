package com.example.todochecklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ItemListsManager itemListsManager = null;
    private ChecklistLister checklistLister = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemListsManager = new ItemListsManager(this);

        ListView checklist = (ListView)findViewById(R.id.checklist_list);
        checklistLister = new ChecklistLister(this, checklist, itemListsManager);
        checklist.setOnItemClickListener(checklistLister);

        if(savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        checklistLister.updateListView();
    }

    public void deleteChecklist(View view){
        checklistLister.deleteChecklist(view);
    }

    public void addChecklist(View view){
        checklistLister.addChecklist(view);
    }
}