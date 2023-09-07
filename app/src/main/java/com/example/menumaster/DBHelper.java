package com.example.menumaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "menumaster.db";

    public DBHelper(@Nullable Context context) {
        super(context, "menumaster.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table restaurants(name TEXT,contact TEXT primary key,address TEXT,landmark TEXT,coordinates TEXT)");
        MyDB.execSQL("create Table users(name TEXT,email TEXT primary key,phone TEXT,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists restaurants");
        onCreate(MyDB);
    }

    public boolean insertData(String name,String email,String phone,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        contentValues.put("password",password);
        System.out.println(contentValues);
        long result = MyDB.insert("users",null,contentValues);
        System.out.println(result);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?",new String[]{email});
        if(cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ? and password = ?",new String[]{email,password});
        if(cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }


    public boolean addRestaurant(String name,String contact,String address,String landmark,String coordinates){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("contact",contact);
        contentValues.put("address",address);
        contentValues.put("landmark",landmark);
        contentValues.put("coordinates",coordinates);

        long result = MyDB.insert("restaurants",null,contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean checkRestaurant(String contact){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from restaurants where contact = ?",new String[]{contact});
        if(cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }
}
