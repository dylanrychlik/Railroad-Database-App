package com.example.testresources;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import androidx.annotation.Nullable;
public class Track_destination extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "new_england_railroad.db";
    public static final String TABLE_NAME = "Track_destination ";
    public static final String COL_1 = "Track_ID";
    public static final String COL_2 = "Station_Destination_ID";

    public Track_destination(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + "Track_destination (Station_Destination_ID INTEGER PRIMARY KEY AUTOINCREMENT,Track_ID TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ "student_table");
        onCreate(db);
    }

    public boolean insertData(String Track_ID, String Station_Destination_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,Station_Destination_ID);

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
        Cursor res = db.rawQuery("SELECT Track_ID, COUNT(Station_Destination_ID) FROM Track_destination GROUP BY Track_ID",null);
        return res;
    }
    public Cursor Having(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Station_Destination_ID), Track_ID FROM Track_destination GROUP BY Station_city HAVING SUM(Track_ID) > 5",null);
        return res;
    }
    public Cursor Join(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Track_destination.Track_ID, Train_schedules.Train_ID, Train_schedules.Track_ID FROM Train_schedules INNER JOIN Track_destination ON Train_schedules.Track_ID=Track_destination.Track_ID",null);
        return res;
    }
    public Cursor LeftJoin(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Track_destination.Track_ID, Train_schedules.Train_ID, Train_schedules.Track_ID FROM Train_schedules INNER JOIN Track_destination ON Train_schedules.Track_ID=Track_destination.Track_ID",null);
        return res;
    }

    public Cursor In(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM `Track_destination` WHERE Track_ID IN ('1', '3', '5');",null);
        return res;
    }

    public boolean updateData(String Track_ID, String Station_Destination_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Station_Destination_ID);
        contentValues.put(COL_2,Track_ID);

        db.update(TABLE_NAME,contentValues,"Station_Destination_ID  = ?",new String[]{Station_Destination_ID});
        return true;
    }
    public Integer deleteData(String Station_Destination_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Station_Destination_ID = ?",new String[]{Station_Destination_ID});
    }
}

