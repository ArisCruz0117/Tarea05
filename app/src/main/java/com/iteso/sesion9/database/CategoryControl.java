package com.iteso.sesion9.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteso.sesion9.beans.Category;

import java.util.ArrayList;

public class CategoryControl {

    public void addCategory(Category category, DataBaseHandler dh){
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.KEY_CATEGORY_ID, category.getId());
        values.put(DataBaseHandler.KEY_CATEGORY_NAME, category.getName());

        db.insert(DataBaseHandler.TABLE_CATEGORY,null,values);

        try{
            db.close();
        }catch (Exception e){

        }
    }

    public ArrayList<Category> getCategories(DataBaseHandler dh){
        ArrayList<Category> categories = new ArrayList<>();
        SQLiteDatabase db = dh.getReadableDatabase();

        String select = "SELECT " + DataBaseHandler.KEY_CATEGORY_ID + ","
                + DataBaseHandler.KEY_CATEGORY_NAME
                + " FROM " + DataBaseHandler.TABLE_CATEGORY;

        Cursor cursor = db.rawQuery(select, null);
        while(cursor.moveToNext()){
            Category category = new Category();
            category.setId(cursor.getInt(0));
            category.setName(cursor.getString(1));
            categories.add(category);
        }

        try{
            cursor.close();
            db.close();
        }catch (Exception e){

        }

        return categories;
    }

    public Category getCategoryById(int categoryId, DataBaseHandler dh){
        Category category = new Category();

        SQLiteDatabase db = dh.getReadableDatabase();

        String select = "SELECT " + DataBaseHandler.KEY_CATEGORY_ID + ","
                + DataBaseHandler.KEY_CATEGORY_NAME
                + " FROM " + DataBaseHandler.TABLE_CATEGORY
                + " WHERE " + DataBaseHandler.KEY_CATEGORY_ID + " = "+ categoryId;

        Cursor cursor = db.rawQuery(select, null);

        cursor.moveToNext();

        category.setId(cursor.getInt(0));
        category.setName(cursor.getString(1));

        try{
            cursor.close();
            db.close();
        }catch (Exception e){

        }

        return category;
    }
}
