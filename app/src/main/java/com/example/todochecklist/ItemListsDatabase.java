package com.example.todochecklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemListsDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "todoapp.sqlite";
    private static final int DB_VERSION = 1;
    private static final String TABLE_CHECKLISTNAMES = "checklistnames";
    private static final String TABLE_ITEMLISTS = "itemlists";

    // fields
    private static final String FIELD_ID = "id";
    private static final String FIELD_CHECKLISTNAME = "checklist_name";
    private static final String FIELD_CHECK = "is_checked";
    private static final String FIELD_LABEL = "label";

    public ItemListsDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(String.format("CREATE TABLE %s (" +
                FIELD_CHECKLISTNAME + " TEXT PRIMARY KEY)", TABLE_CHECKLISTNAMES));

        db.execSQL(String.format("CREATE TABLE %s (" +
                FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FIELD_CHECKLISTNAME + " TEXT REFERENCES %s(checklist_name) ON DELETE CASCADE ON UPDATE CASCADE," +
                FIELD_CHECK + " TEXT," +
                FIELD_LABEL + " TEXT)", TABLE_ITEMLISTS, TABLE_CHECKLISTNAMES));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion != newVersion){
            db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_ITEMLISTS));
            db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_CHECKLISTNAMES));
            onCreate(db);
        }
    }

    public void addChecklistName(ChecklistNames checklistNames){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(FIELD_CHECKLISTNAME, checklistNames.getChecklistName());

            long id = db.insert(TABLE_CHECKLISTNAMES, null, values);
            if(id == -1){
                throw new IllegalStateException("addChecklistName: cannot be inserted.");
            }
        } finally {
            db.close();
        }
    }

    public void addChecklistName(String checklistName){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(FIELD_CHECKLISTNAME, checklistName);

            long id = db.insert(TABLE_CHECKLISTNAMES, null, values);
            if(id == -1){
                throw new IllegalStateException("addChecklistName: cannot be inserted.");
            }
        } finally {
            db.close();
        }
    }

    public void addItem(ItemLists itemLists){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(FIELD_CHECKLISTNAME, itemLists.getChecklistName());
            values.put(FIELD_CHECK, itemLists.getCheckAsString());
            values.put(FIELD_LABEL, itemLists.getLabel());

            long id = db.insert(TABLE_ITEMLISTS, null, values);
            if(id == -1){
                throw new IllegalArgumentException("addItem: cannot be inserted.");
            }
        } finally {
            db.close();
        }
    }

    public void updateChecklistName(String oldName, String newName){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = FIELD_CHECKLISTNAME + " = '" + oldName + "'";
        try{
            ContentValues values = new ContentValues();
            values.put(FIELD_CHECKLISTNAME, newName);

            int nrows = db.update(TABLE_CHECKLISTNAMES, values, whereClause, null);
        } finally {
            db.close();
        }
    }

    public void deleteChecklistNameWhereNameIs(String name){
        String whereClause = FIELD_CHECKLISTNAME + " = '" + name + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLE_ITEMLISTS, whereClause, null);
            db.delete(TABLE_CHECKLISTNAMES, whereClause, null);
        } finally {
            db.close();
        }
    }

    public void deleteItem(String checklistName, String label){
        String whereClause = FIELD_CHECKLISTNAME + " = '" + checklistName + "' AND " +
                FIELD_LABEL + " = '" + label + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLE_ITEMLISTS, whereClause, null);
        } finally {
            db.close();
        }
    }

    public int getNumberOfCheck(String checklistName){
        int count = 0;
        String query = String.format("SELECT count(*) FROM %s WHERE %s = '%s'",
                TABLE_ITEMLISTS, FIELD_CHECKLISTNAME, checklistName);

        SQLiteDatabase db = this.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery(query, null);
            try{
                if(cursor.moveToFirst()){
                    count = Integer.parseInt(cursor.getString(0));
                }
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }

        return count;
    }

    public int getNumberOfCheckIsTrue(String checklistName){
        int count = 0;
        String query = String.format("SELECT count(*) FROM %s WHERE %s = '%s' AND %s = 'true'",
                TABLE_ITEMLISTS, FIELD_CHECKLISTNAME, checklistName, FIELD_CHECK);

        SQLiteDatabase db = this.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery(query, null);
            try {
                if(cursor.moveToFirst()){
                    count = Integer.parseInt(cursor.getString(0));
                }
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }

        return count;
    }

    public ArrayList<String> getChecklistNames(){
        ArrayList<String> ret_string = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_CHECKLISTNAMES);

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(query, null);
            try {
                if(cursor.moveToFirst()){
                    do {
                        ret_string.add(cursor.getString(0));
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }

        return ret_string;
    }

    public List<ItemLists> getItemLists(String checklistName){
        List<ItemLists> list = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE %s = '%s'",
                TABLE_ITEMLISTS, FIELD_CHECKLISTNAME, checklistName);

        SQLiteDatabase db = this.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery(query, null);
            try{
                ItemLists itemLists = null;
                if(cursor.moveToFirst()){
                    do{
                        itemLists = new ItemLists();
                        itemLists.setId(Integer.parseInt(cursor.getString(0)));
                        itemLists.setChecklistName(cursor.getString(1));
                        itemLists.setCheckByString(cursor.getString(2));
                        itemLists.setLabel(cursor.getString(3));
                        list.add(itemLists);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }

        return list;
    }

    public ArrayList<String> getItemLabels(String checklistName){
        ArrayList<String> labels = new ArrayList<>();
        String query = String.format("SELECT %s FROM %s WHERE %s = '%s'",
                FIELD_LABEL, TABLE_ITEMLISTS, FIELD_CHECKLISTNAME, checklistName);

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(query, null);
            try {
                if(cursor.moveToFirst()){
                    do {
                        labels.add(cursor.getString(0));
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }

        return labels;
    }

    public String getChecklistNameAt(int position){
        List<String> names = getChecklistNames();
        if(names.size() >= position+1){
            return names.get(position);
        }
        return null;
    }
}
