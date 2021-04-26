package com.example.testresources;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class TrackActivity extends AppCompatActivity {
Button back;
    DatabaseHelper myDb;
    EditText Track_ID,Track_Length,Track_type,Average_Track_Speed_Limit,Station_start_ID;
    Button btnAddData;
    Button btnViewAll;
    Button btnviewUpdate;
    Button btnDelete;
    Button sumbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_select);

        Track_ID =(EditText)findViewById(R.id.editTextTextPersonName5);
        Track_Length =(EditText)findViewById(R.id.editTextTextPersonName1);
        Track_type =(EditText)findViewById(R.id.editTextTextPersonName2);
        Average_Track_Speed_Limit = (EditText)findViewById(R.id.editTextTextPersonName3);
        Station_start_ID = (EditText)findViewById(R.id.editTextTextPersonName4);
        btnAddData =(Button)findViewById(R.id.button_add);
        btnViewAll =(Button)findViewById(R.id.button_viewall);
        btnviewUpdate = (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_delete);
        DeleteData();
        UpdateData();
        AddData();
        viewAll();
        myDb = new DatabaseHelper(this);

        select_operation();
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackActivity.this,MainActivity.class);
                startActivity(intent);
            }

        });
    }
    public void select_operation(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        List<String> table = new ArrayList<>();

        table.add(0,"Choose an operation: ");
        table.add("Count number of Tracks:");
        table.add("Group by of Tracks via Cities:");
        table.add("Number of Tracks by city having an ID greater than 5:");
        table.add("Join of stations and tracks:");
        table.add("Left Join of stations and tracks:");
        table.add("Max number of tracks:");
        table.add("Sum of tracks:");
        table.add("In of stations and tracks:");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,table);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose an operation: "))
                {

                }
                else {
                    //on selected
                    String item = parent.getItemAtPosition(position).toString();

                    Toast.makeText(parent.getContext(),"selected"+item,Toast.LENGTH_SHORT).show();

                    if (parent.getItemAtPosition(position).equals("Count number of Tracks:")){
                        countTrack();
                    }
                    if (parent.getItemAtPosition(position).equals("Group by of Tracks via Cities:")){
                        GroupBy();
                    }
                    if (parent.getItemAtPosition(position).equals("Number of Tracks by city having an ID greater than 5:")){
                        havingTrack();
                    }
                    if (parent.getItemAtPosition(position).equals("Join of stations and tracks:")){
                        joinTrack();
                    }
                    if (parent.getItemAtPosition(position).equals("Left Join of stations and tracks:")){
                        leftjoinStation();
                    }
                    if (parent.getItemAtPosition(position).equals("Max number of tracks:")){
                        rightjoinStation();
                    }
                    if (parent.getItemAtPosition(position).equals("Sum of tracks:")){
                        unionStation();
                    }
                    if (parent.getItemAtPosition(position).equals("In of stations and tracks:")){
                        inStation();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData_Tracks(Track_ID.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(TrackActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(TrackActivity.this,"Data not deleted",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void UpdateData(){
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData_Tracks(Track_ID.getText().toString(),Track_Length.getText().toString(), Track_type.getText().toString(),Average_Track_Speed_Limit.getText().toString(),Station_start_ID.getText().toString());
                        if (isUpdate==true)
                            Toast.makeText(TrackActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(TrackActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData_Tracks(Track_ID.getText().toString(),Track_Length.getText().toString(), Track_type.getText().toString(),Average_Track_Speed_Limit.getText().toString(),Station_start_ID.getText().toString());
                        if (isInserted==true)
                            Toast.makeText(TrackActivity.this,"Data inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(TrackActivity.this,"Data not inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void viewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData_Tracks();
                if(res.getCount() == 0){
                    //show message
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Track_ID:"+res.getString(0)+"\n");
                    buffer.append("Track_Length:"+res.getString(1)+"\n");
                    buffer.append("Track_type:"+res.getString(2)+"\n");
                    buffer.append("Average_Track_Speed_Limit:"+res.getString(3)+"\n\n");
                    buffer.append("Station_start_ID:"+res.getString(4)+"\n\n");
                }
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void countTrack(){

        Cursor res = myDb.countTracks_Tracks();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Number of Tracks in table :"+res.getString(0)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void GroupBy(){

        Cursor res = myDb.GroupBy_Tracks();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Station_start_ID :"+res.getString(0)+"\n");
            buffer.append("Track_Length:"+res.getString(1)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void havingTrack(){

        Cursor res = myDb.Having_Tracks();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Track ID :"+res.getString(0)+"\n");
            buffer.append("Track Length:"+res.getString(1)+"\n");


        }
        showMessage("Data",buffer.toString());
    }
    public void joinTrack(){

        Cursor res = myDb.Join_Tracks();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Station ID:"+res.getString(0)+"\n");
            buffer.append("Average Track Speed Limit:"+res.getString(1)+"\n");
            buffer.append("Station start ID:"+res.getString(2)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void leftjoinStation(){

        Cursor res = myDb.LeftJoin_Tracks();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Station ID :"+res.getString(0)+"\n");
            buffer.append("Average Track Speed Limit:"+res.getString(1)+"\n");
        }
        showMessage("Data",buffer.toString());
    }
    public void rightjoinStation(){

        Cursor res = myDb.RightJoin_Tracks();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Max tracks:"+res.getString(0)+"\n");


        }
        showMessage("Data",buffer.toString());
    }
    public void unionStation(){

        Cursor res = myDb.Union_Tracks();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Sum tracks :"+res.getString(0)+"\n");



        }
        showMessage("Data",buffer.toString());
    }
    public void inStation(){

        Cursor res = myDb.In_Tracks();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Track Length:"+res.getString(0)+"\n");

        }
        showMessage("Data",buffer.toString());
    }



    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}




