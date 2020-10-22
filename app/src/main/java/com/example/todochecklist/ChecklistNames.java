package com.example.todochecklist;

public class ChecklistNames {
    private String checklist_name = "";

    ChecklistNames(String name){
        checklist_name = name;
    }

    public String getChecklistName(){
        return checklist_name;
    }

    public void setChecklistName(String name){
        checklist_name = name;
    }
}
