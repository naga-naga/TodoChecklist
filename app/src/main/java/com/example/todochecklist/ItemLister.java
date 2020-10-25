package com.example.todochecklist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ItemLister {
    private ItemListActivity itemListActivity = null;
    private ListView itemList = null;
    private ItemListsManager itemListsManager = null;
    private String checklistName = "";

    public ItemLister(ItemListActivity itemListActivity, ListView listView, ItemListsManager itemListsManager, String checklistName){
        this.itemListActivity = itemListActivity;
        this.itemList = listView;
        this.itemListsManager = itemListsManager;
        this.checklistName = checklistName;
    }

    void updateListView(){
        List<ItemLists> itemLists = itemListsManager.getItemLists(checklistName);
        ArrayList<Items> items = new ArrayList<>();
        CustomAdapter adapter = new CustomAdapter(itemListActivity);

        adapter.setItems(items);
        itemList.setAdapter(adapter);

        for(ItemLists checklist_item : itemLists){
            Items item = new Items();
            item.setCheckboxText(checklist_item.getLabel());
            item.setCheck(checklist_item.getCheck());
            items.add(item);
        }

        adapter.notifyDataSetChanged();
    }

    void addItem(View view) {
        final View addItemView = itemListActivity.getLayoutInflater().inflate(R.layout.checklist_adder, null, false);
        final TextInputLayout textInputLayout = (TextInputLayout)addItemView.findViewById(R.id.checklist_adder_layout);
        textInputLayout.setHint(itemListActivity.getString(R.string.add_item_hint));

        AlertDialog.Builder builder = new AlertDialog.Builder(itemListActivity);
        builder.setTitle(R.string.add_item_title)
                .setView(addItemView)
                .setPositiveButton(R.string.add,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextInputEditText textInputEditText = (TextInputEditText) addItemView.findViewById(R.id.checklist_adder_text);
                                String itemName = textInputEditText.getText().toString();

                                try {
                                    if (!(itemName.equals("") || itemName.matches("^\\s+$"))) {
                                        ItemLists item = new ItemLists();
                                        item.setChecklistName(checklistName);
                                        item.setCheck(false);
                                        item.setLabel(itemName.trim().replace("\n", ""));
                                        itemListsManager.addItem(item);
                                    }
                                } catch (IllegalStateException e) {
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

    void deleteItem(View view){
        DialogFragment dialogFragment = new DeleteItemDialogFragment(itemListsManager, this, checklistName);
        dialogFragment.show(itemListActivity.getSupportFragmentManager(), "DeleteItemDialog");
    }
}
