package com.example.huzaifa.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class water extends Fragment {

    TextView total;
    Button add,remove;
    int totamount;
    DatabaseReference dref;

    public static water newInstance() {
        water water = new water();
        return water;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_water,container,false);


    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dref = db.getReference();
        total = (TextView) getView().findViewById(R.id.total);
        add = (Button) getView().findViewById(R.id.addglass);
        remove = (Button) getView().findViewById(R.id.removeglass);


        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                totamount = dataSnapshot.child("Users").child(FDBAccess.uid).child("WaterConsumed").getValue(int.class);
                total.setText("Total water consumed\n= " + String.valueOf(totamount) + "ml");
                if (totamount == 0) remove.setEnabled(false);
                else remove.setEnabled(true);
                FDBAccess.data = dataSnapshot.child("Users").child(FDBAccess.uid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.addglass:
                        totamount += FDBAccess.data.child("GlassSize").getValue(int.class);
                        break;
                    case R.id.removeglass:
                        totamount-=FDBAccess.data.child("GlassSize").getValue(int.class);
                        break;
                }
                FDBAccess.mref.child("WaterConsumed").setValue(totamount);

            }
        };
        add.setOnClickListener(listener);
        remove.setOnClickListener(listener);
    }

}
