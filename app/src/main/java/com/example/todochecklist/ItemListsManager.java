package com.example.todochecklist;

import java.util.ArrayList;
import java.util.List;

public class ItemListsManager {
    private ItemListsDatabase itemListDB = null;

    public ItemListsManager(MainActivity context){
        itemListDB = new ItemListsDatabase(context);
    }

    public synchronized void addChecklistName(ChecklistNames checklistNames){
        itemListDB.addChecklistName(checklistNames);
    }

    public synchronized void addItem(ItemLists itemLists){
        itemListDB.addItem(itemLists);
    }

    public synchronized void updateChecklistName(String oldName, String newName){
        itemListDB.updateChecklistName(oldName, newName);
    }

    public synchronized void deleteChecklistNameWhereNameIs(String name){
        itemListDB.deleteChecklistNameWhereNameIs(name);
    }

    public synchronized void deleteItem(String checklistName, String label){
        itemListDB.deleteItem(checklistName, label);
    }

    public synchronized int getNumberOfCheck(String checklistName){
        return itemListDB.getNumberOfCheck(checklistName);
    }

    public synchronized int getNumberOfCheckIsTrue(String checklistName){
        return itemListDB.getNumberOfCheckIsTrue(checklistName);
    }

    public synchronized ArrayList<String> getChecklistNames(){
        return itemListDB.getChecklistNames();
    }

    public synchronized List<ItemLists> getItemLists(String checklistName){
        return itemListDB.getItemLists(checklistName);
    }

    public synchronized String getChecklistNameAt(int position){
        return itemListDB.getChecklistNameAt(position);
    }

}
