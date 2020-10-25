package com.example.todochecklist;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class ItemListActivity extends AppCompatActivity {
    private String checklistName;
    private String newChecklistName;
    private ItemLister itemLister;
    private ItemListsManager itemListsManager;
    private TextView checklistNameTextView;
    private ListView itemListerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        Intent intent = getIntent();
        checklistName = intent.getStringExtra(ChecklistLister.CHECKLIST_NAME);

        newChecklistName = checklistName;

        itemListsManager = new ItemListsManager(this);

        checklistNameTextView = findViewById(R.id.itemlist_checklist_name);
        checklistNameTextView.setText(checklistName);

        itemListerListView = findViewById(R.id.itemlist_list);
        itemLister = new ItemLister(this, itemListerListView, itemListsManager, checklistName);

    }

    @Override
    protected void onStart(){
        super.onStart();
        itemLister.updateListView();
    }

    @Override
    protected void onPause(){
        super.onPause();
        itemListsManager.updateChecklistName(checklistName, newChecklistName);

        for(int i = 0; i < itemListerListView.getCount(); i++){
            CheckBox checkBox = itemListerListView.getChildAt(i).findViewById(R.id.item_checkbox);
            itemListsManager.updateCheck(newChecklistName, checkBox.getText().toString(), checkBox.isChecked());
        }

        int numOfCheck = itemListsManager.getNumberOfCheck(newChecklistName);
        int numOfTrue = itemListsManager.getNumberOfCheckIsTrue(newChecklistName);
        notifyChecklist(newChecklistName,
                String.format(Locale.US,"%d/%d", numOfTrue, numOfCheck),
                numOfCheck,
                numOfTrue,
                itemListsManager.getIdOfChecklistName(newChecklistName));
    }

    void notifyChecklist(String title, String text, int max, int progress, int id){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyChannelID")
                .setSmallIcon(R.drawable.checkbox_icon) // アイコン．必須．
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setProgress(max, progress, false); // true にすると通知タップ時に自動で通知を消す．

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());
    }

    public void renameChecklistName(View view){
        final View renameView = getLayoutInflater().inflate(R.layout.checklist_adder, null, false);
        final TextInputLayout textInputLayout = (TextInputLayout)renameView.findViewById(R.id.checklist_adder_layout);
        final TextInputEditText textInputEditText = (TextInputEditText)renameView.findViewById(R.id.checklist_adder_text);
        textInputLayout.setHint(getString(R.string.add_checklist_hint));
        textInputEditText.setText(checklistName);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.rename_checklist_title)
                .setView(renameView)
                .setPositiveButton(R.string.rename,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                newChecklistName = textInputEditText.getText().toString();

                                if(!(newChecklistName.equals("") || newChecklistName.matches("^\\s+$"))){
                                    checklistNameTextView.setText(newChecklistName.trim().replace("\n", ""));
                                }
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Nothing to do
                            }
                        })
                .create()
                .show();
    }

    public void addItem(View view){
        itemLister.addItem(view);
    }

    public void deleteItem(View view){
        itemLister.deleteItem(view);
    }
}
