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


public class PassengerActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    Button back;
    EditText editTextTextPersonName1,editTextTextPersonName2,editTextTextPersonName3,editTextTextPersonName4,editTextTextPersonName5,editTextTextPersonName6,editTextTextPersonName7,editTextTextPersonName8;
    Button btnAddData;
    Button btnViewAll;
    Button btnviewUpdate;
    Button btnDelete;
    Button sumbit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        editTextTextPersonName1 =(EditText)findViewById(R.id.editTextTextPersonName1);
        editTextTextPersonName2 =(EditText)findViewById(R.id.editTextTextPersonName2);
        editTextTextPersonName3 =(EditText)findViewById(R.id.editTextTextPersonName3);
        editTextTextPersonName4 = (EditText)findViewById(R.id.editTextTextPersonName4);
        editTextTextPersonName5 =(EditText)findViewById(R.id.editTextTextPersonName5);
        editTextTextPersonName6 =(EditText)findViewById(R.id.editTextTextPersonName6);
        editTextTextPersonName7 =(EditText)findViewById(R.id.editTextTextPersonName7);
        editTextTextPersonName8 = (EditText)findViewById(R.id.editTextTextPersonName8);


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
                Intent intent = new Intent(PassengerActivity.this,MainActivity.class);
                startActivity(intent);
            }

        });


    }

    public void select_operation(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        List<String> table = new ArrayList<>();

        table.add(0,"Choose an operation: ");
        table.add("Count number of Passenger:");
        table.add("Group by of Passenger via coach:");
        table.add("Number of Passenger by city having an ID greater than 5:");
        table.add("Join of Passenger and Trains:");
        table.add("Left Join of Passenger and Trains:");
        table.add("Max of Passengers:");
        table.add("Sum of Passengers:");
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

                    if (parent.getItemAtPosition(position).equals("Count number of Passenger:")){
                        countPassenger();
                    }
                    if (parent.getItemAtPosition(position).equals("Group by of Passenger via coach:")){
                        GroupBy();
                    }
                    if (parent.getItemAtPosition(position).equals("Number of Passenger by city having an ID greater than 5:")){
                        havingPassenger();
                    }
                    if (parent.getItemAtPosition(position).equals("Join of Passenger and Trains:")){
                        joinPassenger();
                    }
                    if (parent.getItemAtPosition(position).equals("Left Join of Passenger and Trains:")){
                        leftjoinPassenger();
                    }
                    if (parent.getItemAtPosition(position).equals("Max of Passengers:")){
                        rightjoinPassenger();
                    }
                    if (parent.getItemAtPosition(position).equals("Sum of Passengers:")){
                        unionPassenger();
                    }
                    if (parent.getItemAtPosition(position).equals("In of stations and tracks:")){
                        inPassenger();
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
                Integer deletedRows = myDb.deleteData_Passenger(editTextTextPersonName1.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(PassengerActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(PassengerActivity.this,"Data not deleted",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void UpdateData(){
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData_Passenger(editTextTextPersonName1.getText().toString(),editTextTextPersonName2.getText().toString(),
                                editTextTextPersonName3.getText().toString(),editTextTextPersonName4.getText().toString(),editTextTextPersonName5.getText().toString(),editTextTextPersonName6.getText().toString(),
                                editTextTextPersonName7.getText().toString(),editTextTextPersonName8.getText().toString());
                        if (isUpdate==true)
                            Toast.makeText(PassengerActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(PassengerActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData_Passenger(editTextTextPersonName1.getText().toString(),editTextTextPersonName2.getText().toString(),
                                editTextTextPersonName3.getText().toString(),editTextTextPersonName4.getText().toString(),editTextTextPersonName5.getText().toString(),editTextTextPersonName6.getText().toString(),
                                editTextTextPersonName7.getText().toString(),editTextTextPersonName8.getText().toString());
                        if (isInserted==true)
                            Toast.makeText(PassengerActivity.this,"Data inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(PassengerActivity.this,"Data not inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void viewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData_Passenger();
                if(res.getCount() == 0){
                    //show message
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Passenger_ID:"+res.getString(0)+"\n");
                    buffer.append("Train_ID:"+res.getString(1)+"\n");
                    buffer.append("Date:"+res.getString(2)+"\n");
                    buffer.append("from_station:"+res.getString(3)+"\n\n");
                    buffer.append("to_station:"+res.getString(4)+"\n");
                    buffer.append("coach:"+res.getString(5)+"\n");
                    buffer.append("seat:"+res.getString(6)+"\n");
                    buffer.append("passenger_name :"+res.getString(7)+"\n");

                }
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void countPassenger(){

        Cursor res = myDb.countStation_Passenger();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Number of Passengers in table :"+res.getString(0)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void GroupBy(){

        Cursor res = myDb.GroupBy_Passenger();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Passenger_ID:"+res.getString(0)+"\n");
            buffer.append("coach:"+res.getString(1)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void havingPassenger(){

        Cursor res = myDb.Having_Passenger();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Passenger_ID:"+res.getString(0)+"\n");
            buffer.append("coach:"+res.getString(1)+"\n");


        }
        showMessage("Data",buffer.toString());
    }
    public void joinPassenger(){

        Cursor res = myDb.Join_Passenger();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Train_ID:"+res.getString(0)+"\n");
            buffer.append("coach:"+res.getString(1)+"\n");
            buffer.append("seat:"+res.getString(2)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void leftjoinPassenger(){

        Cursor res = myDb.LeftJoin_Passenger();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Train ID :"+res.getString(0)+"\n");
            buffer.append("coach:"+res.getString(1)+"\n");
            buffer.append("seat:"+res.getString(2)+"\n");
        }
        showMessage("Data",buffer.toString());
    }
    public void rightjoinPassenger(){

        Cursor res = myDb.RightJoin_Passenger();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Max pasengers:"+res.getString(0)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void unionPassenger(){

        Cursor res = myDb.Union_Passenger();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Sum pasengers:"+res.getString(0)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void inPassenger(){

        Cursor res = myDb.In_Passenger();
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