package com.example.huzaifa.myapplication;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class stephistory extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    DatabaseReference dref;
    ArrayList steplist,waterlist;
    ArrayList datelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stephistory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.drawable.logo);

        toolbar.setTitle(R.string.app_name);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        listView=(ListView)findViewById(R.id.stephistorylist);

        GenericTypeIndicator<ArrayList<Integer>> t1 = new GenericTypeIndicator<ArrayList<Integer>>() {
        };
        steplist = FDBAccess.data.child("StepHistory").child("Steplist").getValue(t1);

        GenericTypeIndicator<ArrayList<Long>> t2 = new GenericTypeIndicator<ArrayList<Long>>() {
        };
        datelist = FDBAccess.data.child("StepHistory").child("Datelist").getValue(t2);

        GenericTypeIndicator<ArrayList<Integer>> t3 = new GenericTypeIndicator<ArrayList<Integer>>() {
        };
        waterlist = FDBAccess.data.child("StepHistory").child("Waterlist").getValue(t3);
                dataModels= new ArrayList<DataModel>();
                if(steplist.size()>1) {
                    for (int i = 0; i < steplist.size()-1; i++)
                        dataModels.add(new DataModel(Integer.parseInt(steplist.get(i).toString()), Long.parseLong(datelist.get(i).toString()),Integer.parseInt(waterlist.get(i).toString())));
                    adapter = new CustomAdapter(dataModels, getApplicationContext());

                    listView.setAdapter(adapter);
                }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
