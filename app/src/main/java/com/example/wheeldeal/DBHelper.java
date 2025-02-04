package com.example.wheeldeal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Wheeldeal.db";

    public DBHelper( Context context) {
        super(context, "Wheeldeal.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase AppDB) {
        AppDB.execSQL("Create Table Wheeldealusers(email TEXT primary key, profilephoto BLOB, firstname TEXT, lastname TEXT, phonenumber TEXT, address TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase AppDB, int oldVersion, int newVersion) {
        AppDB.execSQL("drop Table if exists Wheeldealusers");
    }

    public Boolean inserData(String email, Bitmap profilephoto, String firstname, String lastname, String phonenumber, String address, String password){
        SQLiteDatabase AppDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("email", email);
//        contentValues.put("profilephoto", (profilephoto));
        contentValues.put("firstname", firstname);
        contentValues.put("lastname" ,lastname);
        contentValues.put("phonenumber", phonenumber);
        contentValues.put("address", address);
        contentValues.put("password", password);

        long result = AppDB.insert("Wheeldealusers", null, contentValues);

        if (result == -1) return false;
        else
            return true;
    }








}
