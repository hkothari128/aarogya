package com.example.huzaifa.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by huzaifa on 03-Mar-17.
 */

public class TimeListener extends BroadcastReceiver{

    DatabaseReference dref;
    Firebase mref;
    int steps;
    int wateramount;
    String uid;
    ArrayList steplist;
    ArrayList datelist,waterlist,calorie_in_list,calorie_out_list;
    Double calorie_in,calorie_out;
    String type;
    @Override
    public void onReceive(Context context, Intent intent) {
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        dref= db.getReference();
        mref=new Firebase("https://myapplication-75bf5.firebaseio.com/");
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            uid = user.getUid();

            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    steps = dataSnapshot.child("Users").child(uid).child("Steps").getValue(int.class);
                    wateramount = dataSnapshot.child("Users").child(uid).child("WaterConsumed").getValue(int.class);
                    calorie_in = dataSnapshot.child("Users").child(uid).child("CaloriesIn").getValue(double.class);
                    calorie_out = dataSnapshot.child("Users").child(uid).child("CaloriesOut").getValue(double.class);
                    GenericTypeIndicator<ArrayList<Integer>> t1 = new GenericTypeIndicator<ArrayList<Integer>>() {
                    };
                    steplist = dataSnapshot.child("Users").child(uid).child("StepHistory").child("Steplist").getValue(t1);
                    GenericTypeIndicator<ArrayList<Long>> t2 = new GenericTypeIndicator<ArrayList<Long>>() {
                    };
                    datelist = dataSnapshot.child("Users").child(uid).child("StepHistory").child("Datelist").getValue(t2);

                    GenericTypeIndicator<ArrayList<Integer>> t3 = new GenericTypeIndicator<ArrayList<Integer>>() {
                    };
                    waterlist = dataSnapshot.child("Users").child(uid).child("StepHistory").child("Waterlist").getValue(t3);
                    GenericTypeIndicator<ArrayList<Double>> t4 = new GenericTypeIndicator<ArrayList<Double>>() {
                    };
                    calorie_in_list = dataSnapshot.child("Users").child(uid).child("StepHistory").child("Calorie_in_list").getValue(t4);
                    GenericTypeIndicator<ArrayList<Double>> t5 = new GenericTypeIndicator<ArrayList<Double>>() {
                    };
                    calorie_out_list = dataSnapshot.child("Users").child(uid).child("StepHistory").child("Calorie_out_list").getValue(t5);

                    calorie_in_list.add(0, calorie_in);
                    calorie_out_list.add(0, calorie_out);
                    waterlist.add(0, wateramount);
                    steplist.add(0, steps);
                    Calendar cal = Calendar.getInstance();

                    cal.add(cal.DATE, -1);
                    datelist.add(0, cal.getTimeInMillis());

                    mref.child("Users").child(uid).child("StepHistory").child("Steplist").setValue(steplist);
                    mref.child("Users").child(uid).child("StepHistory").child("Datelist").setValue(datelist);
                    mref.child("Users").child(uid).child("StepHistory").child("Waterlist").setValue(waterlist);
                    mref.child("Users").child(uid).child("StepHistory").child("Calorie_in_list").setValue(calorie_in_list);
                    mref.child("Users").child(uid).child("StepHistory").child("Calorie_out_list").setValue(calorie_out_list);
                    mref.child("Users").child(uid).child("Steps").setValue(0);
                    mref.child("Users").child(uid).child("WaterConsumed").setValue(0);
                    mref.child("Users").child(uid).child("CaloriesIn").setValue(0);
                    mref.child("Users").child(uid).child("CaloriesOut").setValue(0);
                    mref.child("Users").child(uid).child("TODAY").removeValue();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}
