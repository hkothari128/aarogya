package com.example.huzaifa.myapplication;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class settings extends AppCompatActivity {
    LinearLayout edit,display;
    EditText stepmax,start,end;
    TextView size,dispsize,dispsteps,dispstart,dispend;
    Button plus,minus,savebtn,startbtn,endbtn,editbtn;
    ImageView img,dispimg;
    int gsize,mHour,mMinute,starthour,startminute,endhour,endminute;
    int maxstep,glasssize;
    String starttime,endtime;
    Calendar c;
    TimePickerDialog timePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.drawable.logo);
        toolbar.setTitle(R.string.app_name);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Button bt = new Button(this);
        bt.setText("A Button");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.FILL_PARENT);
        params.gravity = Gravity.RIGHT;
        bt.setLayoutParams(params);
        toolbar.addView(bt);

        edit=(LinearLayout)findViewById(R.id.edit);
        display=(LinearLayout)findViewById(R.id.display);

        dispsteps=(TextView)findViewById(R.id.disp_stepsmax);
        dispsize=(TextView)findViewById(R.id.disp_glass_size);
        dispstart=(TextView)findViewById(R.id.disp_start_time);
        dispend=(TextView)findViewById(R.id.disp_end_time);

        dispimg=(ImageView)findViewById(R.id.disp_glass_image);

        stepmax=(EditText)findViewById(R.id.stepsmax);
        size=(TextView)findViewById(R.id.glass_size);
        start=(EditText)findViewById(R.id.start_time);
        end=(EditText)findViewById(R.id.end_time);
        plus=(Button)findViewById(R.id.plus);
        minus=(Button)findViewById(R.id.minus);
        startbtn=(Button)findViewById(R.id.startbtn);
        endbtn=(Button)findViewById(R.id.endbtn);
        savebtn=(Button)findViewById(R.id.savebtn);
        editbtn=(Button)findViewById(R.id.editbtn);
        img=(ImageView)findViewById(R.id.glass_image);
        gsize=250;

        initialize();

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
                    case R.id.editbtn:
                        editbtn.setEnabled(false);
                        edit.setVisibility(View.VISIBLE);
                        display.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.savebtn:
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
                        initialize();
                        break;
                    case R.id.startbtn:
                        c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        timePickerDialog = new TimePickerDialog(settings.this,
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
                        timePickerDialog = new TimePickerDialog(settings.this,
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
        savebtn.setOnClickListener(listener);
        editbtn.setOnClickListener(listener);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void initialize()
    {
        FDBAccess.dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                maxstep=dataSnapshot.child("Users").child(FDBAccess.uid).child("MaxStep").getValue(int.class);
                glasssize=dataSnapshot.child("Users").child(FDBAccess.uid).child("GlassSize").getValue(int.class);
                stepmax.setText(String.valueOf(maxstep));
                dispsteps.setText(String.valueOf(maxstep));
                size.setText(String.valueOf(glasssize)+" ml");
                dispsize.setText(String.valueOf(glasssize)+" ml");

                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");

                starttime=formatter.format(dataSnapshot.child("Users").child(FDBAccess.uid).child("water_reminder_start").getValue(Long.class));
                endtime=formatter.format(dataSnapshot.child("Users").child(FDBAccess.uid).child("water_reminder_end").getValue(Long.class));
                start.setText(starttime);
                end.setText(endtime);
                dispstart.setText("Start time: "+starttime);
                dispend.setText("Start time: "+endtime);
                editbtn.setEnabled(true);
                display.setVisibility(View.VISIBLE);
                edit.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
