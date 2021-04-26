package com.example.testresources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
public static final String DATABASE_NAME = "test72.db";
    public static final String TABLE_NAME = "Stations";
    public static final String COL_1 = "Station_ID";
    public static final String COL_2 = "Station_name";
    public static final String COL_3 = "Station_city";
    public static final String COL_4 = "Station_state";

    public static final String TABLE_NAME2 = "Tracks";
    public static final String TCOL_1 = "Track_ID";
    public static final String TCOL_2 = "Track_Length";
    public static final String TCOL_3 = "Track_type";
    public static final String TCOL_4 = "Average_Track_Speed_Limit";
    public static final String TCOL_5 = "Station_start_ID";

    public static final String TABLE_NAME3 = "Passenger";
    public static final String PCOL_1 = "Passenger_ID";
    public static final String PCOL_2 = "Train_ID";
    public static final String PCOL_3 = "Date";
    public static final String PCOL_4 = "from_station";
    public static final String PCOL_5 = "to_station";
    public static final String PCOL_6 = "coach";
    public static final String PCOL_7 = "seat";
    public static final String PCOL_8 = "passenger_name";


    public static final String TABLE_NAME4 = "Trains";
    public static final String TrainCOL_1 = "Train_ID";
    public static final String TrainCOL_2 = "Train_name";
    public static final String TrainCOL_3 = "Train_Model";
    public static final String TrainCOL_4 = "Train_Max_speed";
    public static final String TrainCOL_5 = "Train_make";
    public static final String TrainCOL_6 = "Train_Weight";
    public static final String TrainCOL_7 = "Train_Length";
    public static final String TrainCOL_8 = "Traffic_type";

    public static final String TABLE_NAME5 = "Train_schedules";
    public static final String Train_schedules_COL_1 = "Schedule_ID";
    public static final String Train_schedules_COL_2 = "Train_ID";
    public static final String Train_schedules_COL_3 = "Track_ID";
    public static final String Train_schedules_COL_4 = "Station_Start";
    public static final String Train_schedules_COL_5 = "Station_Destination";
    public static final String Train_schedules_COL_6 = "Route_location_start";
    public static final String Train_schedules_COL_7 = "Route_location_destination";
    public static final String Train_schedules_COL_8 = "Timein";
    public static final String Train_schedules_COL_9 = "Timeout";
    public static final String Train_schedules_COL_10 = "Schedule_Sequence_no";

    public static final String TABLE_NAME6 = "Track_destination ";

    public static final String Track_destination_COL_1 = "Station_Destination_ID";
    public static final String Track_destination_COL_2 = "Track_ID";


    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table " + "Stations" + "(Station_ID INTEGER PRIMARY KEY AUTOINCREMENT,Station_name TEXT, Station_city TEXT, Station_state TEXT)");
        db.execSQL("create table " + TABLE_NAME2 + "(Track_ID INTEGER PRIMARY KEY AUTOINCREMENT,Track_Length TEXT,Track_type TEXT, Average_Track_Speed_Limit TEXT, Station_start_ID TEXT)");
        db.execSQL("create table " +TABLE_NAME3 + "(Passenger_ID INTEGER PRIMARY KEY AUTOINCREMENT,Train_ID TEXT, Date TEXT, from_station TEXT, to_station TEXT,coach TEXT,seat TEXT, passenger_name TEXT)");
        db.execSQL("create table " + TABLE_NAME4 + "(Train_ID INTEGER PRIMARY KEY AUTOINCREMENT,Train_name TEXT,Train_Model TEXT, Train_Max_speed TEXT,Train_make TEXT,Train_Weight TEXT, Train_Length TEXT, Traffic_type TEXT)");
        db.execSQL("create table " +  TABLE_NAME5  + "(Schedule_ID INTEGER PRIMARY KEY AUTOINCREMENT, Train_ID TEXT, Track_ID TEXT,Station_Start TEXT, Station_Destination TEXT, Route_location_start TEXT,Route_location_destination TEXT, Timein TEXT, Timeout TEXT,  Schedule_Sequence_no TEXT)");

        db.execSQL("create table " + TABLE_NAME6 +  "(Station_Destination_ID INTEGER PRIMARY KEY AUTOINCREMENT,Track_ID TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ "Stations");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME5);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME6);
        onCreate(db);
    }

    public boolean insertData(String Station_name, String Station_city, String Station_state){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,Station_name);
        contentValues.put(COL_3,Station_city);
        contentValues.put(COL_4,Station_state);
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
        Cursor res = db.rawQuery("SELECT Station_city, COUNT(Station_ID) FROM Stations GROUP BY Station_city",null);
        return res;
    }
    public Cursor Having(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT  COUNT(Station_ID), Station_city FROM Stations GROUP BY Station_city HAVING SUM(Station_ID) > 5",null);
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
    public Cursor RightJoin(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT MAX(Station_ID) FROM Stations;",null);
        return res;
    }
    public Cursor Union(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Sum(Station_ID) FROM Stations;",null);
        return res;
    }
    public Cursor In(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM `Stations` WHERE Station_city IN ('peterboro');",null);
        return res;
    }

    public boolean updateData(String Station_ID,String Station_name, String Station_city, String Station_state){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1,Station_ID);
            contentValues.put(COL_2,Station_name);
            contentValues.put(COL_3,Station_city);
            contentValues.put(COL_4,Station_state);
            db.update(TABLE_NAME,contentValues,"Station_ID = ?",new String[]{Station_ID});
           return true;
        }
        public Integer deleteData(String Station_ID){
            SQLiteDatabase db = this.getWritableDatabase();
          return db.delete(TABLE_NAME, "Station_ID = ?",new String[]{Station_ID});
        }




    public boolean insertData_Tracks(String Track_ID, String Track_Length, String Track_type, String Average_Track_Speed_Limit, String Station_start_ID){
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
    public Cursor getAllData_Tracks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select *  from " + TABLE_NAME2,null);

        return res;
    }

    public Cursor countTracks_Tracks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME2,null);
        return res;
    }
    public Cursor GroupBy_Tracks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Track_Length,COUNT(Track_ID) FROM `Tracks` GROUP BY Track_type;",null);
        return res;
    }
    public Cursor Having_Tracks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(Track_ID), Track_type FROM `Tracks` GROUP BY Track_type HAVING  SUM(Track_ID) > 5",null);
        return res;
    }
    public Cursor Join_Tracks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Stations.Station_ID, Tracks.Average_Track_Speed_Limit, Tracks.Station_start_ID FROM Tracks INNER JOIN Stations ON Tracks.Station_start_ID=Stations.Station_ID;",null);
        return res;
    }
    public Cursor LeftJoin_Tracks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Stations.Station_ID, Tracks.Average_Track_Speed_Limit FROM Tracks LEFT JOIN Stations ON Tracks.Station_start_ID=Stations.Station_ID ORDER BY Tracks.Average_Track_Speed_Limit;",null);
        return res;
    }
    public Cursor RightJoin_Tracks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT MAX(Track_ID) FROM Tracks;",null);
        return res;
    }
    public Cursor Union_Tracks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Track_ID) FROM Tracks;",null);
        return res;
    }
    public Cursor In_Tracks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM `Tracks` WHERE Track_Length IN ('frieght');",null);
        return res;
    }

    public boolean updateData_Tracks(String Track_ID,String Track_Length, String Track_type, String Average_Track_Speed_Limit,String Station_start_ID){
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
    public Integer deleteData_Tracks(String Station_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME2, "Track_ID = ?",new String[]{Station_ID});
    }

    public boolean insertData_Passenger(String Passenger_ID, String Train_ID, String Date,String from_station, String to_station, String coach, String seat, String passenger_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PCOL_1,Passenger_ID);
        contentValues.put(PCOL_2,Train_ID);
        contentValues.put(PCOL_3,Date);
        contentValues.put(PCOL_4,from_station);
        contentValues.put(PCOL_5,to_station);
        contentValues.put(PCOL_6,coach);
        contentValues.put(PCOL_7,seat);
        contentValues.put(PCOL_8,passenger_name);
        long result = db.insert(TABLE_NAME3,null,contentValues);
        if (result == -1) {
            return false;
        }else{ return  true;}
    }
    public Cursor getAllData_Passenger(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select *  from " + TABLE_NAME3,null);

        return res;
    }

    public Cursor countStation_Passenger(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME3,null);
        return res;
    }
    public Cursor GroupBy_Passenger(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(Passenger_ID), coach FROM `Passenger` GROUP BY coach",null);
        return res;
    }
    public Cursor Having_Passenger(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Passenger_ID), coach FROM `Passenger` GROUP BY coach HAVING SUM(Passenger_ID) > 5",null);
        return res;
    }
    public Cursor Join_Passenger(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Trains.Train_ID, Passenger.coach, Passenger.seat FROM Passenger INNER JOIN Trains ON Passenger.Train_ID=Trains.Train_ID;",null);
        return res;
    }
    public Cursor LeftJoin_Passenger(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Trains.Train_ID, Passenger.coach, Passenger.seat FROM Passenger LEFT JOIN Trains ON Passenger.Train_ID=Trains.Train_ID ORDER BY Trains.Train_ID;",null);
        return res;
    }
    public Cursor RightJoin_Passenger(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT MAX(Passenger_ID) FROM Passenger;",null);
        return res;
    }
    public Cursor Union_Passenger(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Passenger_ID) FROM Passenger;",null);
        return res;
    }
    public Cursor In_Passenger(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM `Passenger` WHERE Train_ID IN ('1', '3', '5');",null);
        return res;
    }

    public boolean updateData_Passenger(String Passenger_ID, String Train_ID, String Date,String from_station, String to_station, String coach, String seat, String passenger_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PCOL_1,Passenger_ID);
        contentValues.put(PCOL_2,Train_ID);
        contentValues.put(PCOL_3,Date);
        contentValues.put(PCOL_4,from_station);

        contentValues.put(PCOL_5,to_station);
        contentValues.put(PCOL_6,coach);
        contentValues.put(PCOL_7,seat);
        contentValues.put(PCOL_8,passenger_name);
        db.update(TABLE_NAME3,contentValues,"Passenger_ID = ?",new String[]{Passenger_ID});
        return true;
    }
    public Integer deleteData_Passenger(String Passenger_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME3, "Passenger_ID = ?",new String[]{Passenger_ID});
    }




    public boolean insertData_Train(String Train_ID,String Train_name, String Train_Model,String Train_Max_speed, String Train_make, String Train_Weight, String Train_Length,String Traffic_type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TrainCOL_1,Train_ID);
        contentValues.put(TrainCOL_2,Train_name);
        contentValues.put(TrainCOL_3,Train_Model);
        contentValues.put(TrainCOL_4,Train_Max_speed);
        contentValues.put(TrainCOL_5,Train_make);
        contentValues.put(TrainCOL_6,Train_Weight);
        contentValues.put(TrainCOL_7,Train_Length);
        contentValues.put(TrainCOL_8,Traffic_type);
        long result = db.insert(TABLE_NAME4,null,contentValues);
        if (result == -1) {
            return false;
        }else{ return  true;}
    }
    public Cursor getAllData_Train(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select *  from " + TABLE_NAME4,null);

        return res;
    }

    public Cursor countStation_Train(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME4,null);
        return res;
    }
    public Cursor GroupBy_Train(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(Train_ID), Train_name FROM `Trains` GROUP BY Train_name;",null);
        return res;
    }
    public Cursor Having_Train(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Train_ID), Train_name FROM `Trains` GROUP BY Train_name HAVING SUM(Train_ID) > 5",null);
        return res;
    }
    public Cursor Join_Train(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Trains.Train_ID, Passenger.coach, Passenger.seat FROM Passenger INNER JOIN Trains ON Passenger.Train_ID=Trains.Train_ID",null);
        return res;
    }
    public Cursor LeftJoin_Train(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Trains.Train_ID, Passenger.coach, Passenger.seat FROM Passenger LEFT JOIN Trains ON Passenger.Train_ID=Trains.Train_ID ORDER BY Trains.Train_ID;",null);
        return res;
    }
    public Cursor RightJoin_Train(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT MAX(Train_ID) FROM Trains;",null);
        return res;
    }
    public Cursor Union_Train(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Train_ID) FROM Trains;",null);
        return res;
    }
    public Cursor In_Train(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM `Trains`\n"
                + "WHERE Train_ID IN ('1', '3', '5');",null);
        return res;
    }

    public boolean updateData_Train(String Train_ID,String Train_name, String Train_Model, String Train_Max_speed,String Train_make, String Train_Weight, String Train_Length,String Traffic_type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TrainCOL_1,Train_ID);
        contentValues.put(TrainCOL_2,Train_name);
        contentValues.put(TrainCOL_3,Train_Model);
        contentValues.put(TrainCOL_4,Train_Max_speed);
        contentValues.put(TrainCOL_5,Train_make);
        contentValues.put(TrainCOL_6,Train_Weight);
        contentValues.put(TrainCOL_7,Train_Length);
        contentValues.put(TrainCOL_8,Traffic_type);
        db.update(TABLE_NAME4,contentValues,"Train_ID= ?",new String[]{Train_ID});
        return true;
    }
    public Integer deleteData_Train(String Train_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME4, "Train_ID = ?",new String[]{Train_ID});
    }



    //THIS IS THE TRAIN SCHEDULE CLASS


    public boolean insertData(String Schedule_ID, String Train_ID, String Track_ID, String Station_Start, String Route_location_start,String Station_Destination, String Route_location_destination, String Timein, String Timeout, String Schedule_Sequence_no){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Train_schedules_COL_1,Schedule_ID);
        contentValues.put(Train_schedules_COL_2,Train_ID);
        contentValues.put(Train_schedules_COL_3,Track_ID);
        contentValues.put(Train_schedules_COL_4,Station_Start);


        contentValues.put(Train_schedules_COL_5,Route_location_start);
        contentValues.put(Train_schedules_COL_6,Station_Destination);

        contentValues.put(Train_schedules_COL_7,Route_location_destination);
        contentValues.put(Train_schedules_COL_8,Timein);
        contentValues.put(Train_schedules_COL_9,Timeout);
        contentValues.put(Train_schedules_COL_10,Schedule_Sequence_no);
        long result = db.insert(TABLE_NAME5,null,contentValues);
        if (result == -1) {
            return false;
        }else{ return  true;}
    }
    public Cursor getAllData_Train_schedules(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select *  from " + TABLE_NAME5,null);

        return res;
    }

    public Cursor countTrain_schedules(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME5,null);
        return res;
    }
    public Cursor GroupByTrain_schedules(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(Schedule_ID), Route_location_destination FROM `Train_schedules` GROUP BY Route_location_destination;",null);
        return res;
    }
    public Cursor HavingTrain_schedules(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Schedule_ID), Route_location_destination FROM `Train_schedules` GROUP BY Route_location_destination HAVING SUM(Schedule_ID) > 5",null);
        return res;
    }
    public Cursor JoinTrain_schedules(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Track_destination.Track_ID, Train_schedules.Train_ID, Train_schedules.Track_ID FROM Train_schedules INNER JOIN Track_destination ON Train_schedules.Track_ID=Track_destination.Track_ID;",null);
        return res;
    }
    public Cursor LeftJoinTrain_schedules(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Track_destination.Track_ID, Train_schedules.Train_ID, Train_schedules.Track_ID FROM Train_schedules LEFT JOIN Track_destination ON Train_schedules.Track_ID=Track_destination.Track_ID;",null);
        return res;
    }
    public Cursor RightJoinTrain_schedules(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT MAX(Schedule_ID) FROM Train_schedules;",null);
        return res;
    }
    public Cursor UnionTrain_schedules(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Schedule_ID) FROM Train_schedules;",null);
        return res;
    }
    public Cursor InTrain_schedules(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM `Train_schedules` WHERE Schedule_ID IN ('1', '3', '5');",null);
        return res;
    }

    public boolean updateDataTrain_schedules(String Schedule_ID, String Train_ID, String Track_ID, String Station_Start, String Route_location_start,String Station_Destination, String Route_location_destination, String Timein, String Timeout, String Schedule_Sequence_no){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Train_schedules_COL_1,Schedule_ID);
        contentValues.put(Train_schedules_COL_2,Train_ID);
        contentValues.put(Train_schedules_COL_3,Track_ID);
        contentValues.put(Train_schedules_COL_4,Station_Start);

        contentValues.put(Train_schedules_COL_5,Station_Destination);
        contentValues.put(Train_schedules_COL_6,Route_location_start);


        contentValues.put(Train_schedules_COL_7,Route_location_destination);
        contentValues.put(Train_schedules_COL_8,Timein);
        contentValues.put(Train_schedules_COL_9,Timeout);
        contentValues.put(Train_schedules_COL_10,Schedule_Sequence_no);
        db.update(TABLE_NAME5,contentValues,"Schedule_ID = ?",new String[]{Schedule_ID});
        return true;
    }
    public Integer deleteDataTrain_schedules(String Schedule_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME5, "Schedule_ID = ?",new String[]{Schedule_ID});
    }

//Track destination

    public boolean insertData_Track_destination(String Track_ID, String Station_Destination_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Track_destination_COL_1,Station_Destination_ID);
        contentValues.put(Track_destination_COL_2,Track_ID);

        long result = db.insert(TABLE_NAME6,null,contentValues);
        if (result == -1) {
            return false;
        }else{ return  true;}
    }
    public Cursor getAllData_Track_destination(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select *  from " + TABLE_NAME6,null);

        return res;
    }

    public Cursor count_Track_destination(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME6,null);
        return res;
    }
    public Cursor GroupBy_Track_destination(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Track_ID, COUNT(Station_Destination_ID) FROM Track_destination GROUP BY Track_ID",null);
        return res;
    }
    public Cursor Having_Track_destination(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Station_Destination_ID), Track_ID FROM Track_destination GROUP BY Track_ID HAVING SUM(Track_ID) > 5",null);
        return res;
    }
    public Cursor Join_Track_destination(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Track_destination.Track_ID, Train_schedules.Train_ID, Train_schedules.Track_ID FROM Train_schedules INNER JOIN Track_destination ON Train_schedules.Track_ID=Track_destination.Track_ID",null);
        return res;
    }
    public Cursor LeftJoin_Track_destination(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Track_destination.Track_ID, Train_schedules.Train_ID, Train_schedules.Track_ID FROM Train_schedules INNER JOIN Track_destination ON Train_schedules.Track_ID=Track_destination.Track_ID",null);
        return res;
    }
    public Cursor RightJoin_Track_destination(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT MAX(Station_Destination_ID) FROM Track_destination;",null);
        return res;
    }
    public Cursor Union_Track_destination(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Station_Destination_ID) FROM Track_destination;",null);
        return res;
    }
    public Cursor In_Track_destination(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM `Track_destination` WHERE Station_Destination_ID IN ('1');",null);
        return res;
    }

    public boolean updateData_Track_destination(String Station_Destination_ID,String Track_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Track_destination_COL_1,Station_Destination_ID);
        contentValues.put(Track_destination_COL_2,Track_ID);

        db.update(TABLE_NAME6,contentValues,"Station_Destination_ID  = ?",new String[]{Station_Destination_ID});
        return true;
    }
    public Integer deleteData_Track_destination(String Station_Destination_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME6, "Station_Destination_ID = ?",new String[]{Station_Destination_ID});
    }
    }



