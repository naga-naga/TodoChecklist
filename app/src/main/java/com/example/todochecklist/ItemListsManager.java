package com.example.todochecklist;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemListsManager {
    private ItemListsDatabase itemListDB = null;

    public ItemListsManager(Context context){
        itemListDB = new ItemListsDatabase(context);
    }

    public synchronized void addChecklistName(ChecklistNames checklistNames){
        itemListDB.addChecklistName(checklistNames);
    }

    public synchronized void addChecklistName(String checklistName){
        itemListDB.addChecklistName(checklistName);
    }

    public synchronized void addItem(ItemLists itemLists){
        itemListDB.addItem(itemLists);
    }

    public synchronized void updateChecklistName(String oldName, String newName){
        itemListDB.updateChecklistName(oldName, newName);
    }

    public synchronized void updateCheck(String checklistName, String label, boolean check){
        itemListDB.updateCheck(checklistName, label, check);
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

    public synchronized int getIdOfChecklistName(String checklistName){
        return itemListDB.getIdOfChecklistName(checklistName);
    }

    public synchronized List<ItemLists> getItemLists(String checklistName){
        return itemListDB.getItemLists(checklistName);
    }

    public synchronized ArrayList<String> getItemLabels(String checklistName){
        return itemListDB.getItemLabels(checklistName);
    }

    public synchronized String getChecklistNameAt(int position){
        return itemListDB.getChecklistNameAt(position);
    }

}
