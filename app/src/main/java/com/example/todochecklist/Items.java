package com.example.todochecklist;

public class Items {
    private long id;
    private String checkboxText;
    private boolean check;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getCheckboxText(){
        return checkboxText;
    }

    public void setCheckboxText(String text){
        checkboxText = text;
    }

    public boolean getCheck(){
        return check;
    }

    public void setCheck(boolean check){
        this.check = check;
    }
}
