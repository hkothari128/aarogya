package com.example.huzaifa.myapplication;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



/**
 * Created by huzaifa on 05-Mar-17.
 */

public class FDBAccess extends Service{
    public static DatabaseReference dref;
    public static Firebase mref;
    public static FirebaseUser user;
    FirebaseAuth mauth;
    FirebaseDatabase db;
    public static String uid;
    public static com.google.firebase.database.DataSnapshot data;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate( ) {
        super.onCreate();
        mauth = FirebaseAuth.getInstance();
        user = mauth.getCurrentUser();
        uid = user.getUid();
        mref = new Firebase("https://myapplication-75bf5.firebaseio.com/").child("Users").child(uid);

        db = FirebaseDatabase.getInstance();
        dref = db.getReference();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                data=dataSnapshot.child("Users").child(uid);
                if(data!=null)
                    Log.d("DATa","NOT NULL");
                else
                    Log.d("DATA","NULL CHHE");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return START_NOT_STICKY;
    }
    public static void getdata()
    {
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                data=dataSnapshot.child("Users").child(uid);
                if(data!=null)
                    Log.d("DATa","NOT NULL");
                else
                    Log.d("DATA","NULL CHHE");
                return;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
