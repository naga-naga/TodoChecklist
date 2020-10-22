package com.example.todochecklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

        notifyChecklist("title", "text", 100, 20);

        ArrayList<String> items = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            items.add("item: " + i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        checklist.setAdapter(adapter);
    }

    void notifyChecklist(String title, String text, int max, int progress){
        Intent intent = new Intent(this, ItemListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyChannelID")
                .setSmallIcon(R.drawable.ic_launcher_foreground) // アイコン．必須．
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setProgress(max, progress, false); // true にすると通知タップ時に自動で通知を消す．

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}