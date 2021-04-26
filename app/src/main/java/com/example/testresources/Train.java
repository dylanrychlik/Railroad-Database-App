package com.example.testresources;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import androidx.annotation.Nullable;


public class Train  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "new_england_railroad.db";
    public static final String TABLE_NAME = "Trains";
    public static final String COL_1 = "Train_ID";
    public static final String COL_2 = "Train_name";
    public static final String COL_3 = "Train_Model";
    public static final String COL_4 = "Train_make";
    public static final String COL_5 = "Train_Max_speed";
    public static final String COL_6 = "Train_Weight";
    public static final String COL_7 = "Train_Length";
    public static final String COL_8 = "Traffic_type";

    public Train(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + "Trains" + "(Train_ID INTEGER PRIMARY KEY AUTOINCREMENT,Train_Model TEXT, Train_make TEXT, Train_Max_speed TEXT,Train_Weight TEXT, Train_Length TEXT, Traffic_type TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ "student_table");
        onCreate(db);
    }

    public boolean insertData(String Train_name, String Train_Model, String Train_make,String Train_Max_speed, String Train_Weight, String Train_Length,String Traffic_type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2,Train_name);
        contentValues.put(COL_3,Train_Model);
        contentValues.put(COL_4,Train_make);
        contentValues.put(COL_5,Train_Max_speed);
        contentValues.put(COL_6,Train_Weight);
        contentValues.put(COL_7,Train_Length);
        contentValues.put(COL_8,Traffic_type);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1) {
            return false;
        }else{ return  true;}
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select *  from " + TABLE_NAME,null);

        return res;
    }

    public Cursor countStation(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME,null);
        return res;
    }
    public Cursor GroupBy(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(Train_ID), Train_name FROM `Train` GROUP BY Train_name;",null);
        return res;
    }
    public Cursor Having(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Train_ID), Train_name FROM `Train` GROUP BY Train_name HAVING SUM(Train_ID) > 5",null);
        return res;
    }
    public Cursor Join(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Train.Train_ID, Passenger.coach, Passenger.seat FROM Passenger INNER JOIN Train ON Passenger.Train_ID=Train.Train_ID",null);
        return res;
    }
    public Cursor LeftJoin(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Train.Train_ID, Passenger.coach, Passenger.seat FROM Passenger LEFT JOIN Train ON Passenger.Train_ID=Train.Train_ID ORDER BY Train.Train_ID;",null);
        return res;
    }

    public Cursor In(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM `Train`\n"
                + "WHERE Train_ID IN ('1', '3', '5');",null);
        return res;
    }

    public boolean updateData(String Train_ID,String Train_name, String Train_Model, String Train_make,String Train_Max_speed, String Train_Weight, String Train_Length,String Traffic_type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Train_ID);
        contentValues.put(COL_2,Train_name);
        contentValues.put(COL_3,Train_Model);
        contentValues.put(COL_4,Train_make);
        contentValues.put(COL_5,Train_Max_speed);
        contentValues.put(COL_6,Train_Weight);
        contentValues.put(COL_7,Train_Length);
        contentValues.put(COL_8,Traffic_type);
        db.update(TABLE_NAME,contentValues,"Train_ID= ?",new String[]{Train_ID});
        return true;
    }
    public Integer deleteData(String Train_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Train_ID = ?",new String[]{Train_ID});
    }

}
