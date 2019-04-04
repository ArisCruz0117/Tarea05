package com.iteso.sesion9.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteso.sesion9.beans.City;
import com.iteso.sesion9.beans.Store;

import java.util.ArrayList;

public class StoreControl {

    public long addStore(Store store, DataBaseHandler dh) {
        long inserted = 0;
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.KEY_STORE_CITY, store.getCity().getId());
        values.put(DataBaseHandler.KEY_STORE_LAT, store.getLatitude());
        values.put(DataBaseHandler.KEY_STORE_LNG, store.getLongitude());
        values.put(DataBaseHandler.KEY_STORE_NAME, store.getName());
        values.put(DataBaseHandler.KEY_STORE_PHONE, store.getPhone());
        values.put(DataBaseHandler.KEY_STORE_THUMBNAIL, store.getThumbnail());
        // Inserting Row
        inserted = db.insert(DataBaseHandler.TABLE_STORE, null, values);
        // Closing database connection
        try {db.close();} catch (Exception e) {}
        db = null; values = null;
        return inserted;
    }

    public void deleteStore(int idStore, DataBaseHandler dh) {
        SQLiteDatabase db = dh.getWritableDatabase();
        db.delete(DataBaseHandler.TABLE_STORE, DataBaseHandler.KEY_STORE_ID
                + " = ?", new String[] { String.valueOf(idStore) });
        try {
            db.close();
        } catch (Exception e) {
        }
        db = null;
    }

    public int updateStore(Store store, DataBaseHandler dh) {
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.KEY_STORE_CITY, store.getCity().getId());
        values.put(DataBaseHandler.KEY_STORE_LAT, store.getLatitude());
        values.put(DataBaseHandler.KEY_STORE_LNG, store.getLongitude());
        values.put(DataBaseHandler.KEY_STORE_NAME, store.getName());
        values.put(DataBaseHandler.KEY_STORE_PHONE, store.getPhone());
        values.put(DataBaseHandler.KEY_STORE_THUMBNAIL, store.getThumbnail());
        // Updating row
        int count = db.update(DataBaseHandler.TABLE_STORE, values,
                DataBaseHandler.KEY_STORE_ID + " = ?",
                new String[] { String.valueOf(store.getId()) });
        try { db.close();} catch (Exception e) {}
        db = null;
        return count;
    }

    public Store getStoreById(int idStore, DataBaseHandler dh) {
        Store store = new Store();
        String selectQuery = "SELECT S." + DataBaseHandler.KEY_STORE_ID + ","
                + "S." + DataBaseHandler.KEY_STORE_LAT + ","
                + "S." + DataBaseHandler.KEY_STORE_LNG + ","
                + "S." + DataBaseHandler.KEY_STORE_NAME + ","
                + "S." + DataBaseHandler.KEY_STORE_PHONE + ","
                + "S." + DataBaseHandler.KEY_STORE_THUMBNAIL + ","
                + "C." + DataBaseHandler.KEY_CITY_ID + ","
                + "C." + DataBaseHandler.KEY_CITY_NAME + " FROM "
                + DataBaseHandler.TABLE_STORE + " S, "
                + DataBaseHandler.TABLE_CITY + " C WHERE S."
                + DataBaseHandler.KEY_STORE_ID + "= " + idStore
                + " AND S." + DataBaseHandler.KEY_STORE_CITY
                + " = C." + DataBaseHandler.KEY_CITY_ID;
        SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            store.setId(cursor.getInt(0));
            store.setLatitude(cursor.getDouble(1));
            store.setLongitude(cursor.getDouble(2));
            store.setName(cursor.getString(3));
            store.setPhone(cursor.getString(4));
            store.setThumbnail(cursor.getInt(5));
            City city = new City();
            city.setId(cursor.getInt(6));
            city.setName(cursor.getString(7));
            store.setCity(city);
        }
        try {cursor.close();db.close();
        } catch (Exception e) {}
        db = null;
        cursor = null;
// return store
        return store;
    }

    }
