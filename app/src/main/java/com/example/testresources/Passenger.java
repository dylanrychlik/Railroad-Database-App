package com.example.testresources;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import androidx.annotation.Nullable;

public class Passenger extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "new_england_railroad.db";
    public static final String TABLE_NAME = "Passenger";
    public static final String COL_1 = "Passenger_ID";
    public static final String COL_2 = "Train_ID";
    public static final String COL_3 = "Date";
    public static final String COL_4 = "from_station";
    public static final String COL_5 = "to_station";
    public static final String COL_6 = "coach";
    public static final String COL_7 = "seat";
    public static final String COL_8 = "passenger_name";


    public Passenger(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + "Passenger" + "(Passenger_ID INTEGER PRIMARY KEY AUTOINCREMENT,Train_ID TEXT, Date TEXT, from_station TEXT, to_station TEXT,coach TEXT,seat TEXT, passenger_name TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ "student_table");
        onCreate(db);
    }

    public boolean insertData(String Passenger_ID, String Train_ID, String Date,String from_station, String to_station, String coach, String seat, String passenger_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Passenger_ID);
        contentValues.put(COL_2,Train_ID);
        contentValues.put(COL_3,Date);
        contentValues.put(COL_4,from_station);
        contentValues.put(COL_5,to_station);
        contentValues.put(COL_6,coach);
        contentValues.put(COL_7,seat);
        contentValues.put(COL_8,passenger_name);
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
        Cursor res = db.rawQuery("SELECT COUNT(Passenger_ID), coach FROM `Passenger` GROUP BY coach",null);
        return res;
    }
    public Cursor Having(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Passenger_ID), coach FROM `Passenger` GROUP BY coach HAVING SUM(Passenger_ID) > 5",null);
        return res;
    }
    public Cursor Join(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Train.Train_ID, Passenger.coach, Passenger.seat FROM Passenger INNER JOIN Train ON Passenger.Train_ID=Train.Train_ID;",null);
        return res;
    }


    public Cursor In(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM `Passenger` WHERE Train_ID IN ('1', '3', '5');",null);
        return res;
    }

    public boolean updateData(String Passenger_ID, String Train_ID, String Date,String from_station, String to_station, String coach, String seat, String passenger_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Passenger_ID);
        contentValues.put(COL_2,Train_ID);
        contentValues.put(COL_3,Date);
        contentValues.put(COL_4,from_station);

        contentValues.put(COL_5,to_station);
        contentValues.put(COL_6,coach);
        contentValues.put(COL_7,seat);
        contentValues.put(COL_8,passenger_name);
        db.update(TABLE_NAME,contentValues,"Passenger_ID = ?",new String[]{Passenger_ID});
        return true;
    }
    public Integer deleteData(String Station_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Passenger_ID = ?",new String[]{Station_ID});
    }

}
