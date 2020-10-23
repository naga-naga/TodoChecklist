package com.example.todochecklist;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ItemLister {
    private ItemListActivity itemListActivity = null;
    private ListView itemList = null;
    private ItemListsManager itemListsManager = null;
    private String checklist_name = "";

    public ItemLister(ItemListActivity itemListActivity, ListView listView, ItemListsManager itemListsManager, String checklist_name){
        this.itemListActivity = itemListActivity;
        this.itemList = listView;
        this.itemListsManager = itemListsManager;
        this.checklist_name = checklist_name;
    }

    void updateListView(){
        List<ItemLists> itemLists = itemListsManager.getItemLists(checklist_name);
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
}
