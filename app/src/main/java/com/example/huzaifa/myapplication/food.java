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

public class food extends DialogFragment {

    DataBaseHelper myDbHelper;
    TextView tv;
    //EditText input;
    AutoCompleteTextView autotv;
    Button log;
    homepage homepage;
    public food(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_food, container,false);
        getDialog().setCanceledOnTouchOutside(true);
        //getDialog().setTitle("DIALOG");
        tv=(TextView)view.findViewById(R.id.tv);
        autotv =(AutoCompleteTextView)view.findViewById(R.id.autotv);
        log=(Button)view.findViewById(R.id.caloriebtn);
        autotv.setHint("Food list");
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
                log.setEnabled(false);
                ArrayList<String> result=new ArrayList<String>();
                if(!autotv.getText().toString().equalsIgnoreCase(""))
                    result=myDbHelper.getFoodName(autotv.getText().toString());
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
                tv.setText("Calories: "+String.valueOf(myDbHelper.getCalorieFood(autotv.getText().toString()))+" Cal");
                log.setEnabled(true);
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float calories_gained=myDbHelper.getCalorieFood(autotv.getText().toString());
                String key=FDBAccess.mref.child("TODAY").push().getKey();
                Calendar calendar=Calendar.getInstance();
                ActivityModel foodob=new ActivityModel("Food",autotv.getText().toString(),calendar.getTimeInMillis(),calories_gained);
                FDBAccess.mref.child("TODAY").child(key).setValue(foodob);
                FDBAccess.mref.child("CaloriesIn").setValue(FDBAccess.data.child("CaloriesIn").getValue(float.class)+calories_gained);
                Toast.makeText(getActivity().getApplicationContext(),"Successfully logged in food and calorie",Toast.LENGTH_SHORT).show();

                getDialog().dismiss();
            }
        });

        return view;
    }

}
