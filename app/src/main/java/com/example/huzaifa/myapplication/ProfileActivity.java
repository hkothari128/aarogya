package com.example.huzaifa.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView BMI;
    TextView height, email;
    TextView weight, age, Name;
    float hght, wght, bmi, agev;
    String text, name, emailv;
    Button btn ;
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.drawable.logo);
        toolbar.setTitle(R.string.app_name);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dref = db.getReference();

        email = (TextView)findViewById(R.id.email_text);
        age = (TextView)findViewById(R.id.age_value);
        BMI = (TextView)findViewById(R.id.bmi_value);
        height = (TextView)findViewById(R.id.height_value);
        weight = (TextView)findViewById(R.id.weight_value);
        Name = (TextView)findViewById(R.id.name);
        btn=(Button)findViewById(R.id.edit_profile);

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FDBAccess.data = dataSnapshot.child("Users").child(FDBAccess.uid);

                name = FDBAccess.data.child("Name").getValue(String.class);
                hght = FDBAccess.data.child("Details").child("Height").getValue(float.class);
                wght = FDBAccess.data.child("Details").child("Weight").getValue(float.class);
                agev = FDBAccess.data.child("Details").child("Age").getValue(float.class);
                emailv = FDBAccess.user.getEmail().toString();
                Log.d("EMAILLLLL",emailv);
                email.setText(emailv);
                Name.setText(name);
                height.setText(String.valueOf(hght));
                weight.setText(String.valueOf(wght));
                age.setText(String.valueOf(agev));

                bmiCalculator();

                BMI.setText(String.valueOf(bmi)+text);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });
    }

    public void bmiCalculator(){
        bmi = (wght*10000)/(hght*hght);

        if(bmi<18.5){
            text = " " + "(Underweight)";
        }
        else if(bmi>=18.5&&bmi<24.9){
            text = " " + "(Normal)";
        }
        else if(bmi>=24.9&&bmi<30){
            text = " " + "(Overweight)";
        }
        else {
            text = " " + "(Obese)";
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
