package com.example.wheeldeal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "WheelDealDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "AddsTable";

    private static final String ID = "id";
    private static final String CATEGORY = "category";
    private static final String BRAND = "brand";
    private static final String IMG1 = "addImg1";
    private static final String IMG2 = "addImg2";
    private static final String IMG3 = "addImg3";
    private static final String MILAGE = "milage";
    private static final String CAPACITY = "capacity";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String LOCATION = "location";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY + " TEXT, "
                + BRAND + " TEXT, "
                + IMG1 + "BLOB, "
                + IMG2 + "BLOB, "
                + IMG3 + "BLOB, "
                + MILAGE + " INTEGER, "
                + CAPACITY + " INTEGER, "
                + DESCRIPTION + " TEXT, "
                + PRICE + " REAL, "
                + LOCATION + " TEXT "
                +")";

        db.execSQL(query);
    }

    void createNewAdd(String category, String brand, byte[] img1, byte[] img2, byte[] img3, int milage, String capacity, String description, String price, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CATEGORY, category);
        values.put(BRAND, brand);
        values.put(IMG1, img1);
        values.put(IMG2, img2);
        values.put(IMG3, img3);
        values.put(MILAGE, milage);
        values.put(CAPACITY, capacity);
        values.put(PRICE, price);
        values.put(DESCRIPTION, description);
        values.put(LOCATION, location);

        try {
            db.insert(TABLE_NAME, null, values);
        } finally {
            db.close();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
    }

    //Read Database for Home Adds
    Cursor readAllData(){
        String query = " SELECT * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            db.rawQuery(query,null);
        }
        return cursor;
    }






}
