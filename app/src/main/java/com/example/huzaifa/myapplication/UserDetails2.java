package com.example.huzaifa.myapplication;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class UserDetails2 extends AppCompatActivity {
    EditText stepmax,start,end;
    TextView size;
    Button plus,minus,btn,startbtn,endbtn;
    ImageView img;
    int gsize,mHour,mMinute,starthour,startminute,endhour,endminute;
    Calendar c;
    TimePickerDialog timePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details2);
        stepmax=(EditText)findViewById(R.id.stepsmax);
        size=(TextView)findViewById(R.id.glass_size);
        start=(EditText)findViewById(R.id.start_time);
        end=(EditText)findViewById(R.id.end_time);
        plus=(Button)findViewById(R.id.plus);
        minus=(Button)findViewById(R.id.minus);
        startbtn=(Button)findViewById(R.id.startbtn);
        endbtn=(Button)findViewById(R.id.endbtn);
        btn=(Button)findViewById(R.id.nextbtn);
        img=(ImageView)findViewById(R.id.glass_image);
        gsize=250;


        minus.setEnabled(false); size.setText(String.valueOf(gsize)+" ml");
        View.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.plus:
                        minus.setEnabled(true);
                        gsize+=250;
                        size.setText(String.valueOf(gsize)+" ml");
                        img.getLayoutParams().height+=20;
                        img.getLayoutParams().width+=20;
                        if(gsize==1000)
                            plus.setEnabled(false);
                        break;
                    case R.id.minus:
                        plus.setEnabled(true);
                        gsize-=250;
                        size.setText(String.valueOf(gsize)+" ml");
                        img.getLayoutParams().height-=20;
                        img.getLayoutParams().width-=20;
                        if(gsize==250)
                            minus.setEnabled(false);
                        break;
                    case R.id.nextbtn:
                        ArrayList<Integer> steplist = new ArrayList<Integer>();
                        ArrayList<Long> datelist = new ArrayList<Long>();
                        ArrayList<Integer> waterlist = new ArrayList<Integer>();
                        ArrayList<Double> calorie_in_list = new ArrayList<Double>();
                        ArrayList<Double> calorie_out_list = new ArrayList<Double>();
                        Calendar calendar = Calendar.getInstance();
                        waterlist.add(0);
                        steplist.add(0);
                        datelist.add(calendar.getTimeInMillis());
                        calorie_in_list.add(0.0);
                        calorie_out_list.add(0.0);
                        Log.e("DATELIST", String.valueOf(datelist));
                        FDBAccess.mref.child("StepHistory").child("Steplist").setValue(steplist);
                        FDBAccess.mref.child("StepHistory").child("Datelist").setValue(datelist);
                        FDBAccess.mref.child("StepHistory").child("Waterlist").setValue(waterlist);
                        FDBAccess.mref.child("StepHistory").child("Calorie_in_list").setValue(calorie_in_list);
                        FDBAccess.mref.child("StepHistory").child("Calorie_out_list").setValue(calorie_out_list);


                        FDBAccess.mref.child("MaxStep").setValue(Integer.parseInt(stepmax.getText().toString()));
                        FDBAccess.mref.child("GlassSize").setValue(gsize);
                        FDBAccess.mref.child("WaterConsumed").setValue(0);
                        FDBAccess.mref.child("CaloriesIn").setValue(0);
                        FDBAccess.mref.child("CaloriesOut").setValue(0);
                        c=Calendar.getInstance();
                        c.set(c.HOUR_OF_DAY,starthour);
                        c.set(c.MINUTE,startminute);
                        FDBAccess.mref.child("water_reminder_start").setValue(c.getTimeInMillis());
                        c=Calendar.getInstance();
                        c.set(c.HOUR_OF_DAY,endhour);
                        c.set(c.MINUTE,endminute);
                        FDBAccess.mref.child("water_reminder_end").setValue(c.getTimeInMillis());
                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.startbtn:
                         c = Calendar.getInstance();
                         mHour = c.get(Calendar.HOUR_OF_DAY);
                         mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        timePickerDialog = new TimePickerDialog(UserDetails2.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        starthour=hourOfDay;
                                        startminute=minute;
                                        if(hourOfDay==12)
                                            start.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute)+" pm");
                                        else if(hourOfDay==0)
                                            start.setText(String.valueOf(hourOfDay+12) + ":" + String.valueOf(minute)+" am");
                                        else if(hourOfDay>12)
                                            start.setText(String.valueOf(hourOfDay-12) + ":" + String.valueOf(minute)+" pm");
                                        else
                                            start.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute)+" am");

                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                        break;
                    case R.id.endbtn:
                        c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        timePickerDialog = new TimePickerDialog(UserDetails2.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        endhour=hourOfDay;
                                        endminute=minute;
                                        if(hourOfDay==12)
                                            end.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute)+" pm");
                                        else if(hourOfDay==0)
                                            end.setText(String.valueOf(hourOfDay+12) + ":" + String.valueOf(minute)+" am");
                                        else if(hourOfDay>12)
                                            end.setText(String.valueOf(hourOfDay-12) + ":" + String.valueOf(minute)+" pm");
                                        else
                                            end.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute)+" am");

                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                        break;
                }
            }
        };
        startbtn.setOnClickListener(listener);
        endbtn.setOnClickListener(listener);
        plus.setOnClickListener(listener);
        minus.setOnClickListener(listener);
        btn.setOnClickListener(listener);
    }
}
