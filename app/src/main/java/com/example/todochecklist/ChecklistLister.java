package com.example.todochecklist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ChecklistLister implements AdapterView.OnItemClickListener {
    public static final String CHECKLIST_NAME = "com.example.todochecklist.CHECKLIST_NAME";

    private MainActivity mainActivity = null;
    private ListView checklistList = null;
    private ItemListsManager itemListsManager = null;

    public ChecklistLister(MainActivity mainActivity, ListView listView, ItemListsManager itemListsManager){
        this.mainActivity = mainActivity;
        this.checklistList = listView;
        this.itemListsManager = itemListsManager;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Intent intent = new Intent(mainActivity, ItemListActivity.class);
        intent.putExtra(CHECKLIST_NAME, itemListsManager.getChecklistNameAt(position));
        mainActivity.startActivity(intent);
    }

    void updateListView(){
        List<String> checklists = itemListsManager.getChecklistNames();

        ArrayList<String> items = new ArrayList<>();
        for(String item: checklists){
            String label = item;
            items.add(label);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mainActivity, android.R.layout.simple_list_item_1, items);
        checklistList.setAdapter(adapter);
    }

    void deleteChecklist(View view){
        DialogFragment deleteDialogFragment = new DeleteChecklistDialogFragment(itemListsManager, this);
        deleteDialogFragment.show(mainActivity.getSupportFragmentManager(), "DeleteChecklistDialog");
    }

    void addChecklist(View view){
        final View addChecklistView = mainActivity.getLayoutInflater().inflate(R.layout.checklist_adder, null, false);
        final TextInputLayout textInputLayout = (TextInputLayout)addChecklistView.findViewById(R.id.checklist_adder_layout);
        textInputLayout.setHint(mainActivity.getString(R.string.add_checklist_hint));

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle(R.string.add_checklist_title)
                .setView(addChecklistView)
                .setPositiveButton(R.string.add,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextInputEditText textInputEditText = (TextInputEditText)addChecklistView.findViewById(R.id.checklist_adder_text);
                                String checklistName = textInputEditText.getText().toString();

                                try {
                                    if(!(checklistName.equals("") || checklistName.matches("^\\s+$"))){
                                        itemListsManager.addChecklistName(checklistName.trim().replace("\n", ""));
                                    }
                                } catch (IllegalStateException e){
                                    // Error
                                }

                                updateListView();
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
}
