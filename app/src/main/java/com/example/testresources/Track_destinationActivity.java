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


public class Track_destinationActivity extends AppCompatActivity {
    Button back;
    DatabaseHelper myDb;
    EditText editName, editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnviewUpdate;
    Button btnDelete;
    Button sumbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_destination);
        editTextId = (EditText) findViewById(R.id.editTextTextPersonName1);
        editName = (EditText) findViewById(R.id.editTextTextPersonName2);

        btnAddData = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.button_viewall);
        btnviewUpdate = (Button) findViewById(R.id.button_update);
        btnDelete = (Button) findViewById(R.id.button_delete);
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
                Intent intent = new Intent(Track_destinationActivity.this,MainActivity.class);
                startActivity(intent);
            }

        });


    }

    public void select_operation() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        List<String> table = new ArrayList<>();

        table.add(0, "Choose an operation: ");
        table.add("Count number of Track destination:");
        table.add("Group by of Track destination via Station_destination_ID:");
        table.add("Number of Track destination by Station_destination_ID having an ID greater than 5:");
        table.add("Join of Track destination and train schedule:");
        table.add("Left Join of Track destination and train schedule:");
        table.add("Sum of Track destination:");
        table.add("Max of Track destination:");
        table.add("In of Track destination and train schedule:");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, table);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose an operation: ")) {

                } else {
                    //on selected
                    String item = parent.getItemAtPosition(position).toString();

                    Toast.makeText(parent.getContext(), "selected" + item, Toast.LENGTH_SHORT).show();

                    if (parent.getItemAtPosition(position).equals("Count number of Track destination:")) {
                        countTrack_destination();
                    }
                    if (parent.getItemAtPosition(position).equals("Group by of Track destination via Station_destination_ID:")) {
                        GroupBy();
                    }
                    if (parent.getItemAtPosition(position).equals("Number of Track destination by Station_destination_ID having an ID greater than 5:")) {
                        havingTrack_destination();
                    }
                    if (parent.getItemAtPosition(position).equals("Join of Track destination and train schedule:")) {
                        joinTrack_destination();
                    }
                    if (parent.getItemAtPosition(position).equals("Left Join of Track destination and train schedule:")) {
                        leftjoinTrack_destination();
                    }
                    if (parent.getItemAtPosition(position).equals("Max of Track destination:")) {
                        rightjoinTrack_destination();
                    }
                    if (parent.getItemAtPosition(position).equals("Sum of Track destination:")) {
                        unionTrack_destination();
                    }
                    if (parent.getItemAtPosition(position).equals("In of Track destination and train schedule:")) {
                        inTrack_destination();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void DeleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData_Track_destination(editTextId.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(Track_destinationActivity.this, "Data deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Track_destinationActivity.this, "Data not deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void UpdateData() {
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData_Track_destination(editTextId.getText().toString(), editName.getText().toString());
                        if (isUpdate == true)
                            Toast.makeText(Track_destinationActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Track_destinationActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData_Track_destination( editName.getText().toString(),editTextId.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(Track_destinationActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Track_destinationActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData_Track_destination();
                if (res.getCount() == 0) {
                    //show message
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Track_ID:" + res.getString(0) + "\n");
                    buffer.append("Station_Destination_ID:" + res.getString(1) + "\n");

                }
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void countTrack_destination() {

        Cursor res = myDb.count_Track_destination();
        if (res.getCount() == 0) {
            //show message
            showMessage("Error", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Number of tracks in track destination table::" + res.getString(0) + "\n");

        }
        showMessage("Data", buffer.toString());
    }

    public void GroupBy() {

        Cursor res = myDb.GroupBy_Track_destination();
        if (res.getCount() == 0) {
            //show message
            showMessage("Error", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Station destination ID:" + res.getString(0) + "\n");
            buffer.append("Count:" + res.getString(1) + "\n");

        }
        showMessage("Data", buffer.toString());
    }

    public void havingTrack_destination() {

        Cursor res = myDb.Having_Track_destination();
        if (res.getCount() == 0) {
            //show message
            showMessage("Error", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Count:" + res.getString(0) + "\n");
            buffer.append("Station destination ID:" + res.getString(1) + "\n");


        }
        showMessage("Data", buffer.toString());
    }

    public void joinTrack_destination() {
        Cursor res = myDb.Join_Track_destination();
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

    public void leftjoinTrack_destination() {

        Cursor res = myDb.LeftJoin_Track_destination();
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

    public void rightjoinTrack_destination() {

        Cursor res = myDb.RightJoin_Track_destination();
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

    public void unionTrack_destination() {

        Cursor res = myDb.Union_Track_destination();
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

    public void inTrack_destination() {
        Cursor res = myDb.In_Track_destination();
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


    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}