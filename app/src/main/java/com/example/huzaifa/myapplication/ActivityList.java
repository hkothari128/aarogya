package com.example.huzaifa.myapplication;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class ActivityList extends Fragment {

    ListView alv;
    ArrayList<ActivityModel> activityModels;
    private static ActivityAdapter adapter;
    private static FirebaseListAdapter<ArrayList<ActivityModel>> fbadapter;
    Context mcontext;
    public static ActivityList newInstance() {
        ActivityList activityList = new ActivityList();
        return activityList;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list,container,false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        alv=(ListView)getActivity().findViewById(R.id.act_listview);

        mcontext=getActivity().getApplicationContext();

            DatabaseReference dref=FirebaseDatabase.getInstance().getReferenceFromUrl("https://myapplication-75bf5.firebaseio.com/Users/"+FDBAccess.uid+"");
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("mysnap",String.valueOf(dataSnapshot.child("TODAY").getChildrenCount()));
                    populate(dataSnapshot);
                   // for(DataSnapshot ds:dataSnapshot.child("TODAY").getChildren());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





    }

    public void populate(DataSnapshot ds) {
        FDBAccess.getdata();
        if (FDBAccess.data.hasChild("TODAY")) {
            activityModels=new ArrayList<ActivityModel>();
            for (DataSnapshot snapshot : ds.child("TODAY").getChildren()) {
                ActivityModel obj=snapshot.getValue(ActivityModel.class);
                activityModels.add(obj);

            }
            Collections.reverse(activityModels);
            adapter = new ActivityAdapter(activityModels, mcontext);
            alv.setAdapter(adapter);
        }
    }
}
