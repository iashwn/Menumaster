package com.example.menumaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DBHelper extends SQLiteOpenHelper {

    private Bitmap image;
    public static final String DBNAME = "menumaster.db";
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInBytes;

    public DBHelper(@Nullable Context context) {
        super(context, "menumaster.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table restaurants(r_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,contact TEXT,address TEXT,landmark TEXT,coordinates TEXT)");
        MyDB.execSQL("create Table users(u_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT primary key,phone TEXT,password TEXT)");
        MyDB.execSQL("create Table menus(dish_name TEXT,price TEXT,r_id INTEGER,FOREIGN KEY (r_id) REFERENCES restaurants (r_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists restaurants");
        MyDB.execSQL("drop Table if exists menus");
        onCreate(MyDB);
    }

    public boolean insertData(String name,String email,String phone,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        contentValues.put("password",password);
        long result = MyDB.insert("users",null,contentValues);
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
