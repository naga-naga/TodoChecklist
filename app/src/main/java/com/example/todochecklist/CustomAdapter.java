package com.example.todochecklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private  Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Items> items;

    public CustomAdapter(Context context){
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setItems(ArrayList<Items> items){
        this.items = items;
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Object getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent){
        contentView = layoutInflater.inflate(R.layout.items, parent, false);

        CheckBox checkBox = (CheckBox)contentView.findViewById(R.id.item_checkbox);
        checkBox.setText(items.get(position).getCheckboxText());
        checkBox.setChecked(items.get(position).getCheck());

        return contentView;
    }
}
