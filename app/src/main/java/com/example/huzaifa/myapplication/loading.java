package com.example.huzaifa.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by huzaifa on 25-Mar-17.
 */
public class loading extends Activity {

    com.google.firebase.database.DataSnapshot data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Intent i=new Intent(getApplicationContext(),FDBAccess.class);
        startService(i);
        initiate();
    }
    public void initiate()
    {
        DatabaseReference dref;
        Firebase mref;
        FirebaseUser user;
        FirebaseAuth mauth;
        FirebaseDatabase db;
        final String uid;


        final Context mcontext=this;
        mauth = FirebaseAuth.getInstance();
        user = mauth.getCurrentUser();
        uid = user.getUid();

        db = FirebaseDatabase.getInstance();
        dref = db.getReference();
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                data=dataSnapshot.child("Users").child(uid);
                if(data!=null)
                    Log.d("DATa","NOT NULL");
                else
                    Log.d("DATA","NULL CHHE");
                if(!(data.hasChild("Details")))
                {
                    Intent intent=new Intent(getApplicationContext(),UserDetails.class);
                    startActivity(intent);
                }
                else {
                    Intent i = new Intent(getApplicationContext(),homepage.class);

                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
