package com.example.testresources;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import androidx.annotation.Nullable;
public class Train_schedule extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "new_england_railroad.db";
    public static final String TABLE_NAME = "Train_schedules";
    public static final String COL_1 = "Schedule_ID";
    public static final String COL_2 = "Train_ID";
    public static final String COL_3 = "Track_ID";
    public static final String COL_4 = "Station_Start";
    public static final String COL_5 = "Station_Destination";
    public static final String COL_6 = "Route_location_start";
    public static final String COL_7 = "Route_location_destination";
    public static final String COL_8 = "Timein";
    public static final String COL_9 = "Timeout";
    public static final String COL_10 = "Schedule_Sequence_no";


    public Train_schedule(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + "Train_schedules" + "(Station_ID INTEGER PRIMARY KEY AUTOINCREMENT,Schedule_ID TEXT, Train_ID TEXT, Track_ID TEXT,Station_Start TEXT, Station_Destination TEXT, Route_location_start TEXT,Route_location_destination TEXT, Timein TEXT, Timeout TEXT,  Schedule_Sequence_no TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ "student_table");
        onCreate(db);
    }

    public boolean insertData(String Schedule_ID, String Train_ID, String Track_ID, String Station_Start, String Route_location_start,String Station_Destination, String Route_location_destination, String Timein, String Timeout, String Schedule_Sequence_no){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,Train_ID);
        contentValues.put(COL_3,Track_ID);
        contentValues.put(COL_4,Station_Start);


        contentValues.put(COL_5,Route_location_start);
        contentValues.put(COL_6,Station_Destination);

        contentValues.put(COL_7,Route_location_destination);
        contentValues.put(COL_8,Timein);
        contentValues.put(COL_9,Timeout);
        contentValues.put(COL_10,Schedule_Sequence_no);
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

    public Cursor countStation_Destination(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME,null);
        return res;
    }
    public Cursor GroupBy(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(Schedule_ID), Route_location_destination FROM `Train_schedules` GROUP BY Route_location_destination;",null);
        return res;
    }
    public Cursor Having(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Schedule_ID), Route_location_destination FROM `Train_schedules` GROUP BY Route_location_destination HAVING SUM(Schedule_ID) > 5",null);
        return res;
    }
    public Cursor Join(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Track_destination.Track_ID, Train_schedules.Train_ID, Train_schedules.Track_ID FROM Train_schedules INNER JOIN Track_destination ON Train_schedules.Track_ID=Track_destination.Track_ID;",null);
        return res;
    }
    public Cursor LeftJoin(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Track_destination.Track_ID, Train_schedules.Train_ID, Train_schedules.Track_ID FROM Train_schedules LEFT JOIN Track_destination ON Train_schedules.Track_ID=Track_destination.Track_ID;",null);
        return res;
    }
    public Cursor In(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM `Train_schedules` WHERE Schedule_ID IN ('1', '3', '5');",null);
        return res;
    }

    public boolean updateData(String Schedule_ID, String Train_ID, String Track_ID, String Station_Start, String Route_location_start,String Station_Destination, String Route_location_destination, String Timein, String Timeout, String Schedule_Sequence_no){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Schedule_ID);
        contentValues.put(COL_2,Train_ID);
        contentValues.put(COL_3,Track_ID);
        contentValues.put(COL_4,Station_Start);


        contentValues.put(COL_5,Route_location_start);
        contentValues.put(COL_6,Station_Destination);

        contentValues.put(COL_7,Route_location_destination);
        contentValues.put(COL_8,Timein);
        contentValues.put(COL_9,Timeout);
        contentValues.put(COL_10,Schedule_Sequence_no);
        db.update(TABLE_NAME,contentValues,"Schedule_ID = ?",new String[]{Schedule_ID});
        return true;
    }
    public Integer deleteData(String Schedule_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Schedule_ID = ?",new String[]{Schedule_ID});
    }
}
