package com.example.testresources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class Track extends SQLiteOpenHelper{


    public static final String DATABASE_NAME = "new_england_railroad.db";
    public static final String TABLE_NAME2 = "Tracks";
    public static final String TCOL_1 = "Track_ID";
    public static final String TCOL_2 = "Track_Length";
    public static final String TCOL_3 = "Track_type";
    public static final String TCOL_4 = "Average_Track_Speed_Limit";
    public static final String TCOL_5 = "Station_start_ID";



    public Track(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + "Tracks" + "(Track_ID INTEGER PRIMARY KEY AUTOINCREMENT,Track_Length TEXT,Track_type TEXT, Average_Track_Speed_Limit INTEGER, Station_start_ID INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ "Tracks");
        onCreate(db);
    }

    public boolean insertData(String Track_ID,String Track_Length, String Track_type, String Average_Track_Speed_Limit, String Station_start_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TCOL_1,Track_ID);
        contentValues.put(TCOL_2,Track_Length);
        contentValues.put(TCOL_3,Track_type);
        contentValues.put(TCOL_4,Average_Track_Speed_Limit);
        contentValues.put(TCOL_5,Station_start_ID);
        long result = db.insert(TABLE_NAME2,null,contentValues);
        if (result == -1) {
            return false;
        }else{ return  true;}
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select *  from " + TABLE_NAME2,null);

        return res;
    }

    public Cursor countTracks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME2,null);
        return res;
    }
    public Cursor GroupBy(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(Track_ID), Track_Length FROM `Tracks` GROUP BY Track_Length;",null);
        return res;
    }
    public Cursor Having(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Track_ID), Track_Length FROM `Tracks` GROUP BY Track_Length HAVING SUM(Track_ID) > 5",null);
        return res;
    }
    public Cursor Join(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Stations.Station_ID, Tracks.Average_Track_Speed_Limit, Tracks.Station_start_ID FROM Tracks INNER JOIN Stations ON Tracks.Station_start_ID=Stations.Station_ID;",null);
        return res;
    }
    public Cursor LeftJoin(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Stations.Station_ID, Tracks.Average_Track_Speed_Limit FROM Tracks LEFT JOIN Stations ON Tracks.Station_start_ID=Stations.Station_ID ORDER BY Tracks.Average_Track_Speed_Limit;",null);
        return res;
    }

    public Cursor In(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM `Tracks` WHERE Track_Length IN ('10 Miles', '50 Miles', '100 Miles');",null);
        return res;
    }

    public boolean updateData(String Track_ID,String Track_Length, String Track_type, String Average_Track_Speed_Limit,String Station_start_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TCOL_1,Track_ID);
        contentValues.put(TCOL_2,Track_Length);
        contentValues.put(TCOL_3,Track_type);
        contentValues.put(TCOL_4,Average_Track_Speed_Limit);
        contentValues.put(TCOL_5,Station_start_ID);
        db.update(TABLE_NAME2,contentValues,"Track_ID = ?",new String[]{Track_ID});
        return true;
    }
    public Integer deleteData(String Station_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME2, "Track_ID = ?",new String[]{Station_ID});
    }
}


