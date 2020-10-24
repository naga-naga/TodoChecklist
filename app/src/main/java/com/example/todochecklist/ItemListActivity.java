package com.example.todochecklist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ItemListActivity extends AppCompatActivity {
    private String checklistName;
    private String newChecklistName;
    private ItemLister itemLister;
    private ItemListsManager itemListsManager;
    private TextView checklistNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        newChecklistName = "";

        Intent intent = getIntent();
        checklistName = intent.getStringExtra(ChecklistLister.CHECKLIST_NAME);

        itemListsManager = new ItemListsManager(this);

        checklistNameTextView = (TextView)findViewById(R.id.itemlist_checklist_name);
        checklistNameTextView.setText(checklistName);

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

    public void renameChecklistName(View view){
        final View renameView = getLayoutInflater().inflate(R.layout.checklist_adder, null, false);
        final TextInputEditText textInputEditText = (TextInputEditText)renameView.findViewById(R.id.checklist_adder_text);
        textInputEditText.setText(checklistName);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.rename_checklist_title)
                .setView(renameView)
                .setPositiveButton(R.string.rename,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                newChecklistName = textInputEditText.getText().toString();

                                if(!newChecklistName.equals("")){
                                    checklistNameTextView.setText(newChecklistName);
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
