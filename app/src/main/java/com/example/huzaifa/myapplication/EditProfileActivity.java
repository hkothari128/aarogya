package com.example.huzaifa.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    EditText age, name, height, weight;
    DatabaseReference dref;
    String name_val;
    float age_val, height_val, weight_val;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
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

        btn = (Button)findViewById(R.id.updatebtn);
        age = (EditText)findViewById(R.id.age);
        name = (EditText)findViewById(R.id.name);
        height = (EditText)findViewById(R.id.height);
        weight = (EditText)findViewById(R.id.weight);
        age.setText(String.valueOf(FDBAccess.data.child("Details").child("Age").getValue()));
        name.setText(String.valueOf(FDBAccess.data.child("Name").getValue()));
        height.setText(String.valueOf(FDBAccess.data.child("Details").child("Height").getValue()));
        weight.setText(String.valueOf(FDBAccess.data.child("Details").child("Weight").getValue()));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age_val = Float.parseFloat(age.getText().toString());
                height_val = Float.parseFloat(height.getText().toString());
                weight_val = Float.parseFloat(weight.getText().toString());
                name_val = name.getText().toString();

                FDBAccess.mref.child("Details").child("Age").setValue(age_val);
                FDBAccess.mref.child("Details").child("Height").setValue(height_val);
                FDBAccess.mref.child("Details").child("Weight").setValue(weight_val);
                FDBAccess.mref.child("Name").setValue(name_val);

                Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
