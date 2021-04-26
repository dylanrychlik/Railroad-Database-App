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


public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editSurname,editMarks,editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnviewUpdate;
    Button btnDelete;
    Button sumbit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName =(EditText)findViewById(R.id.editTextTextPersonName1);
        editSurname =(EditText)findViewById(R.id.editTextTextPersonName2);
        editMarks =(EditText)findViewById(R.id.editTextTextPersonName3);
        editTextId = (EditText)findViewById(R.id.editTextTextPersonName4);
        btnAddData =(Button)findViewById(R.id.button_add);
        btnViewAll =(Button)findViewById(R.id.button_viewall);
        btnviewUpdate = (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_delete);
        DeleteData();
        UpdateData();
        AddData();
        viewAll();
        myDb = new DatabaseHelper(this);
        select_table();
        select_operation();




    }

    public void select_operation(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        List<String> table = new ArrayList<>();

        table.add(0,"Choose an operation: ");
        table.add("Count number of Stations:");
        table.add("Group by of Stations via Cities:");
        table.add("Number of stations by city having an ID greater than 5:");
        table.add("Join of stations and tracks:");
        table.add("Left Join of stations and tracks:");
        table.add("Max number of stations:");
        table.add("Sum of stations:");
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

                    if (parent.getItemAtPosition(position).equals("Count number of Stations:")){
                        countStation();
                    }
                    if (parent.getItemAtPosition(position).equals("Group by of Stations via Cities:")){
                        GroupBy();
                    }
                    if (parent.getItemAtPosition(position).equals("Number of stations by city having an ID greater than 5:")){
                        havingStation();
                    }
                    if (parent.getItemAtPosition(position).equals("Join of stations and tracks:")){
                        joinStation();
                    }
                    if (parent.getItemAtPosition(position).equals("Left Join of stations and tracks:")){
                        leftjoinStation();
                    }
                    if (parent.getItemAtPosition(position).equals("Max number of stations:")){
                        rightjoinStation();
                    }
                    if (parent.getItemAtPosition(position).equals("Sum of stations:")){
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
    public void select_table(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner3);

        List<String> table = new ArrayList<>();

        table.add(0,"Station");
        table.add("Passenger");
        table.add("Track_destination");
        table.add("Tracks");
        table.add("Train");
        table.add("Train_schedule");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,table);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("choose a table"))
                {

                }
                else {
                    //on selected
                    String item = parent.getItemAtPosition(position).toString();

                    Toast.makeText(parent.getContext(),"selected"+item,Toast.LENGTH_SHORT).show();

                    if (parent.getItemAtPosition(position).equals("Passenger")){
                        Intent intent = new Intent(MainActivity.this, PassengerActivity.class);
                        startActivity(intent);
                    }
                    else if (parent.getItemAtPosition(position).equals("Track_destination")){
                        Intent intent = new Intent(MainActivity.this, Track_destinationActivity.class);
                        startActivity(intent);
                    }
                    else if (parent.getItemAtPosition(position).equals("Train_schedule")){
                        Intent intent = new Intent(MainActivity.this, Train_scheduleActivity.class);
                        startActivity(intent);
                    }
                    else if (parent.getItemAtPosition(position).equals("Train")){
                        Intent intent = new Intent(MainActivity.this, TrainActivity.class);
                        startActivity(intent);
                    }
                    else if (parent.getItemAtPosition(position).equals("Tracks")){
                        Intent intent = new Intent(MainActivity.this, TrackActivity.class);
                        startActivity(intent);
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
                Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not deleted",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void UpdateData(){
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(editTextId.getText().toString(),editName.getText().toString(),
                        editSurname.getText().toString(),editMarks.getText().toString());
                if (isUpdate==true)
                    Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),editMarks.getText().toString());
                          if (isInserted==true)
                              Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_LONG).show();
                          else
                              Toast.makeText(MainActivity.this,"Data not inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void viewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0){
                    //show message
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id:"+res.getString(0)+"\n");
                    buffer.append("Station_name:"+res.getString(1)+"\n");
                    buffer.append("Station_city:"+res.getString(2)+"\n");
                    buffer.append("Station_state:"+res.getString(3)+"\n\n");
                }
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void countStation(){

                Cursor res = myDb.countStation();
                if(res.getCount() == 0){
                    //show message
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Number of stations in table :"+res.getString(0)+"\n");

                }
                showMessage("Data",buffer.toString());
            }
    public void GroupBy(){

        Cursor res = myDb.GroupBy();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("City name:"+res.getString(0)+"\n");
            buffer.append("Count:"+res.getString(1)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void havingStation(){

        Cursor res = myDb.Having();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Count:"+res.getString(0)+"\n");
            buffer.append("City name:"+res.getString(1)+"\n");
        }
        showMessage("Data",buffer.toString());
    }
    public void joinStation(){

        Cursor res = myDb.Join();
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

        Cursor res = myDb.LeftJoin();
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

        Cursor res = myDb.RightJoin();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Max stations:"+res.getString(0)+"\n");

        }
        showMessage("Data",buffer.toString());
    }
    public void unionStation(){

        Cursor res = myDb.Union();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Sum stations :"+res.getString(0)+"\n");



        }
        showMessage("Data",buffer.toString());
    }
    public void inStation(){

        Cursor res = myDb.In();
        if(res.getCount() == 0){
            //show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Track ID :"+res.getString(0)+"\n");

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