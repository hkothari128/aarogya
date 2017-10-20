package com.example.huzaifa.myapplication;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class activities extends DialogFragment {

    DataBaseHelper myDbHelper;
    TextView tv;
    float calories_burnt;
    int duration;
    AutoCompleteTextView autotv;
    EditText hours,minutes;
    Button log,set;
    public activities(){}
   

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_activities, container,false);

        getDialog().setTitle("Simple Dialog");
        tv=(TextView)view.findViewById(R.id.tv);
        autotv =(AutoCompleteTextView)view.findViewById(R.id.autotv);
        log=(Button)view.findViewById(R.id.caloriebtn);
        set=(Button)view.findViewById(R.id.setbtn);
        hours=(EditText)view.findViewById(R.id.hours);
        minutes=(EditText)view.findViewById(R.id.mins);
        autotv.setHint("Activity List");
        log.setEnabled(false);

        try {
            myDbHelper = new DataBaseHelper(getActivity().getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }


        
        autotv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ArrayList<String> result=new ArrayList<String>();
                if(!autotv.getText().toString().equalsIgnoreCase(""))
                    result=myDbHelper.getActivityName(autotv.getText().toString());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.dropdown, result);
                autotv.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        autotv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        duration=Integer.parseInt(hours.getText().toString())*60+Integer.parseInt(minutes.getText().toString());
                        calories_burnt=FDBAccess.data.child("Details").child("Weight").getValue(Float.class);
                        calories_burnt*=myDbHelper.getCalorieActivity(autotv.getText().toString());
                        calories_burnt=calories_burnt*duration/30;
                        tv.setText("Calories: "+String.valueOf(calories_burnt)+" Cal");
                        log.setEnabled(true);
                    }
                });

            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key=FDBAccess.mref.child("TODAY").push().getKey();
                Calendar calendar=Calendar.getInstance();
                ActivityModel foodob=new ActivityModel("Activity",autotv.getText().toString(),calendar.getTimeInMillis(),calories_burnt,duration);
                FDBAccess.mref.child("TODAY").child(key).setValue(foodob);
                FDBAccess.mref.child("CaloriesOut").setValue(FDBAccess.data.child("CaloriesOut").getValue(float.class)+calories_burnt);
                Toast.makeText(getActivity().getApplicationContext(),"Successfully logged in activity and calorie",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });

        return view;
    }

}
