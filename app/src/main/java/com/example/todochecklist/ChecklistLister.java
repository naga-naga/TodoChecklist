package com.example.todochecklist;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
            String label = String.format(Locale.US, "%s\n%d/%d",
                    item,
                    itemListsManager.getNumberOfCheckIsTrue(item),
                    itemListsManager.getNumberOfCheck(item));
            items.add(label);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mainActivity, android.R.layout.simple_list_item_1, items);
        checklistList.setAdapter(adapter);
    }

    void deleteChecklist(View view){
        DialogFragment deleteDialogFragment = new DeleteDialogFragment(itemListsManager, this);
        deleteDialogFragment.show(mainActivity.getSupportFragmentManager(), "DeleteChecklistDialog");
    }
}
