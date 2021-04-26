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


public class Train_scheduleActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editTextTextPersonName1,editTextTextPersonName2,editTextTextPersonName3,editTextTextPersonName4,editTextTextPersonName5,editTextTextPersonName6,editTextTextPersonName7,editTextTextPersonName8,editTextTextPersonName9,editTextTextPersonName10;
    Button back;
    EditText editName,editSurname,editMarks,editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnviewUpdate;
    Button btnDelete;
    Button sumbit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_schedule);
        editTextTextPersonName1 =(EditText)findViewById(R.id.editTextTextPersonName1);
        editTextTextPersonName2 =(EditText)findViewById(R.id.editTextTextPersonName2);
        editTextTextPersonName3 =(EditText)findViewById(R.id.editTextTextPersonName3);
        editTextTextPersonName4 = (EditText)findViewById(R.id.editTextTextPersonName4);
        editTextTextPersonName5 =(EditText)findViewById(R.id.editTextTextPersonName5);
        editTextTextPersonName6 =(EditText)findViewById(R.id.editTextTextPersonName6);
        editTextTextPersonName7 =(EditText)findViewById(R.id.editTextTextPersonName7);
        editTextTextPersonName8 = (EditText)findViewById(R.id.editTextTextPersonName8);
        editTextTextPersonName9 =(EditText)findViewById(R.id.editTextTextPersonName9);
        editTextTextPersonName10 = (EditText)findViewById(R.id.editTextTextPersonName10);
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
                Intent intent = new Intent(Train_scheduleActivity.this,MainActivity.class);
                startActivity(intent);
            }

        });



    }

    public void select_operation(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        List<String> table = new ArrayList<>();

        table.add(0,"Choose an operation: ");
        table.add("Count number of Train schedules:");
        table.add("Group by of Train via Train name:");
        table.add("Number of Train by Train name having an ID greater than 5:");
        table.add("Join of train and passengers:");
        table.add("Left Join of train and passengers:");
        table.add("Max train schedules:");
        table.add("Sum train schedules:");
        table.add("In of train and passengers:");

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

                    if (parent.getItemAtPosition(position).equals("Count number of Train schedules:")){
                        countTrain_schedule();
                    }
                    if (parent.getItemAtPosition(position).equals("Group by of Train via Train name:")){
                        GroupBy();
                    }
                    if (parent.getItemAtPosition(position).equals("Number of Train by Train name having an ID greater than 5:")){
                        havingTrain_schedule();
                    }
                    if (parent.getItemAtPosition(position).equals("Join of train and passengers:")){
                        joinTrain_schedule();
                    }
                    if (parent.getItemAtPosition(position).equals("Left Join of train and passengers:")){
                        leftjoinTrain_schedule();
                    }
                    if (parent.getItemAtPosition(position).equals("Max train schedules:")){
                        rightjoinTrain_schedule();
                    }
                    if (parent.getItemAtPosition(position).equals("Sum train schedules:")){
                        unionTrain_schedule();
                    }
                    if (parent.getItemAtPosition(position).equals("In of train and passengers:")){
                        inTrain_schedule();
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
                Integer deletedRows = myDb.deleteDataTrain_schedules(editTextTextPersonName1.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(Train_scheduleActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Train_scheduleActivity.this,"Data not deleted",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void UpdateData(){
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateDataTrain_schedules(editTextTextPersonName1.getText().toString(),editTextTextPersonName2.getText().toString(),
                                editTextTextPersonName3.getText().toString(),editTextTextPersonName4.getText().toString(),editTextTextPersonName5.getText().toString(),editTextTextPersonName6.getText().toString(),
                                editTextTextPersonName7.getText().toString(),editTextTextPersonName8.getText().toString(),editTextTextPersonName9.getText().toString(),editTextTextPersonName10.getText().toString());
                        if (isUpdate==true)
                            Toast.makeText(Train_scheduleActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Train_scheduleActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editTextTextPersonName1.getText().toString(),editTextTextPersonName2.getText().toString(),
                                editTextTextPersonName3.getText().toString(),editTextTextPersonName4.getText().toString(),editTextTextPersonName5.getText().toString(),editTextTextPersonName6.getText().toString(),
                                editTextTextPersonName7.getText().toString(),editTextTextPersonName8.getText().toString(),editTextTextPersonName9.getText().toString(),editTextTextPersonName10.getText().toString());
                        if (isInserted==true)
                            Toast.makeText(Train_scheduleActivity.this,"Data inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Train_scheduleActivity.this,"Data not inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void viewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData_Train_schedules();
                if(res.getCount() == 0){
                    //show message
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Schedule ID:"+res.getString(0)+"\n");
                    buffer.append("Train ID:"+res.getString(1)+"\n");
                    buffer.append("Track ID:"+res.getString(2)+"\n");
                    buffer.append("Station Start:"+res.getString(3)+"\n\n");

                    buffer.append("Station Destination:"+res.getString(4)+"\n");
                    buffer.append("Route location start:"+res.getString(5)+"\n");
                    buffer.append("Route location destination:"+res.getString(6)+"\n");
                    buffer.append("Timein:"+res.getString(7)+"\n\n");
                    buffer.append("Timeout:"+res.getString(8)+"\n\n");
                    buffer.append("Schedule_Sequence_no:"+res.getString(9)+"\n\n");
                }
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void countTrain_schedule(){

        Cursor res = myDb.countStation();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Number of trains in table:"+res.getString(0)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void GroupBy(){

        Cursor res = myDb.GroupByTrain_schedules();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Route location destination:"+res.getString(0)+"\n");
            buffer.append("Count:"+res.getString(1)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void havingTrain_schedule(){

        Cursor res = myDb.HavingTrain_schedules();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Count:"+res.getString(0)+"\n");
            buffer.append("Route location destination:"+res.getString(1)+"\n");


        }
        showMessage("Data",buffer.toString());
    }
    public void joinTrain_schedule(){

        Cursor res = myDb.JoinTrain_schedules();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Track ID:"+res.getString(0)+"\n");
            buffer.append("Train ID:"+res.getString(1)+"\n");
            buffer.append("Track ID:"+res.getString(2)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void leftjoinTrain_schedule(){

        Cursor res = myDb.LeftJoinTrain_schedules();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Track ID:"+res.getString(0)+"\n");
            buffer.append("Train ID:"+res.getString(1)+"\n");
            buffer.append("Track ID:"+res.getString(2)+"\n");
        }
        showMessage("Data",buffer.toString());
    }
    public void rightjoinTrain_schedule(){

        Cursor res = myDb.RightJoinTrain_schedules();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Max track destination:"+res.getString(0)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void unionTrain_schedule(){

        Cursor res = myDb.UnionTrain_schedules();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Sum track destination:"+res.getString(0)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void inTrain_schedule(){

        Cursor res = myDb.InTrain_schedules();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Train ID:"+res.getString(0)+"\n");

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