package com.example.todochecklist;

public class ItemLists {
    private long id = 0;
    private String checklist_name = "";
    private boolean check = false;
    private String label = "";

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getChecklistName(){
        return checklist_name;
    }

    public void setChecklistName(String name){
        checklist_name = name;
    }

    public boolean getCheck(){
        return check;
    }

    public void setCheck(boolean check){
        this.check = check;
    }

    public String getLabel(){
        return label;
    }

    public void setLabel(String label){
        this.label = label;
    }
}
