package com.example.huzaifa.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;

public class MyService extends Service implements SensorEventListener{

    SensorManager sManager;
    Sensor stepSensor ;
    public int steps;
    AlarmManager am;
    DatabaseReference dref;
    Firebase mref;
    FirebaseUser user;
    String uid;
    long start,end;
    Double calorie_out;
    public MyService(){}
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase db=FirebaseDatabase.getInstance();
         dref= db.getReference();
        mref=new Firebase("https://myapplication-75bf5.firebaseio.com/");

        user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            uid = user.getUid();
            setMidnightAlarm();
            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    steps = dataSnapshot.child("Users").child(uid).child("Steps").getValue(int.class);
                    start = dataSnapshot.child("Users").child(uid).child("water_reminder_start").getValue(long.class);
                    end = dataSnapshot.child("Users").child(uid).child("water_reminder_end").getValue(long.class);

                    Calendar c2 = Calendar.getInstance();
                    Intent i2 = new Intent(getBaseContext(), TimeListener2.class);
                    PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 0, i2, 0);
                    c2.setTimeInMillis(start);
                    c2.set(c2.DATE,Calendar.getInstance().get(Calendar.DATE));
                    Log.d("Reminder time", String.valueOf(c2.getTime()));
                    // Schedule the alarm!
                    AlarmManager am2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                    am2.setInexactRepeating(AlarmManager.RTC_WAKEUP, c2.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent2);

                    Intent cancellationIntent = new Intent(getApplicationContext(), CancelAlarmBroadcastReceiver.class);
                    cancellationIntent.putExtra("key", pendingIntent2);
                    PendingIntent cancellationPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, cancellationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    c2 = Calendar.getInstance();
                    c2.setTimeInMillis(end);
                    c2.set(c2.DATE, Calendar.getInstance().get(Calendar.DATE));
                    Log.d("Reminder end time",String.valueOf(c2.getTime()));
                    AlarmManager am3 = (AlarmManager) getSystemService(ALARM_SERVICE);
                    am3.setInexactRepeating(AlarmManager.RTC_WAKEUP, c2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, cancellationPendingIntent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void setMidnightAlarm()
    {
        Calendar c=Calendar.getInstance();
        Intent i = new Intent(getApplicationContext(), TimeListener.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
        Calendar c1=Calendar.getInstance();
        c1.setTimeInMillis(c1.getTimeInMillis());
        c.set(Calendar.HOUR_OF_DAY,24);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        // Schedule the alarm!
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(user!=null) {
            Log.d("ONSTARTCOMMAND", "AAYA");
            sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            stepSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            sManager.registerListener(MyService.this, stepSensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.hasChild("Users/" + uid + "/StepHistory")) {
                        Log.d("no step history", "no");
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
                        mref.child("Users").child(uid).child("StepHistory").child("Steplist").setValue(steplist);
                        mref.child("Users").child(uid).child("StepHistory").child("Datelist").setValue(datelist);
                        mref.child("Users").child(uid).child("StepHistory").child("Waterlist").setValue(waterlist);
                        mref.child("Users").child(uid).child("StepHistory").child("Calorie_in_list").setValue(calorie_in_list);
                        mref.child("Users").child(uid).child("StepHistory").child("Calorie_out_list").setValue(calorie_out_list);
                    }

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
            return START_STICKY;

    }


    @Override
    public void onSensorChanged(final SensorEvent event) {
        if(user!=null) {
            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    steps = dataSnapshot.child("Users").child(uid).child("Steps").getValue(int.class);
                    new SensorEventLoggerTask().execute(event);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class SensorEventLoggerTask extends
            AsyncTask<SensorEvent, Void, Void> {
        @Override
        protected Void doInBackground(SensorEvent... events) {
            SensorEvent event = events[0];
            Sensor sensor=event.sensor;
            float values[]=event.values;
            int value=-1;
            if(values.length>0){
                value=(int)values[0];
            }
            if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                steps++;
                Log.d("STEPS::",String.valueOf(steps));
                calorie_out=steps*0.044;
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                final String uid=user.getUid();

                mref.child("Users").child(uid).child("Steps").setValue(steps);
                mref.child("Users").child(uid).child("CaloriesOut").setValue(calorie_out);
            }
            return null;
        }
    }
}
