package com.example.huzaifa.myapplication;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by huzaifa on 11-Feb-17.
 */


public class Pedometer extends Fragment {



    private String Uname,uid;
    private int steps;
    ProgressBar progressBar;
    long progressBarStatus;


    int max;
    DatabaseReference dref;
    Firebase mref;

    public static Pedometer newInstance() {
        Pedometer pedometer = new Pedometer();
        return pedometer;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stepsview,container,false);
       
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar=(ProgressBar)getView().findViewById(R.id.pbar);



       final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        mref=new Firebase("https://myapplication-75bf5.firebaseio.com/");
        FirebaseDatabase db=FirebaseDatabase.getInstance();
         dref= db.getReference();


        //***********TEST listener************

        if(FDBAccess.data.hasChild("Steps"))
            steps=FDBAccess.data.child("Steps").getValue(int.class);
        else
            FDBAccess.mref.child("Steps").setValue(0);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                steps = dataSnapshot.child("Users").child(uid).child("Steps").getValue(int.class);
                if(dataSnapshot.child("Users").child(uid).hasChild("MaxStep"))max=dataSnapshot.child("Users").child(uid).child("MaxStep").getValue(int.class);
                progressBar.setMax(max);
                display();
                FDBAccess.data=dataSnapshot.child("Users").child(uid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Uname=FDBAccess.data.child("Name").getValue(String.class);

    }



    private void display() {
        TextView t=(TextView)getView().findViewById(R.id.step);
        t.setTextColor(Color.BLACK);
        t.setText(String.valueOf(steps));

        progressBarStatus=steps;
        progressBar.setProgress(steps);
    }


}
