package com.example.huzaifa.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;

public class UserDetails extends AppCompatActivity {

    EditText agetext,heighttext,weighttext;
    Button btnsubmit,btngo;
    LinearLayout linearLayout;
    TextView agetv,heighttv,weighttv,nametv,bmitv,bmidesctv;
    ImageView iv;
    int age,height,weight;
    double bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        agetext=(EditText)findViewById(R.id.age);
        heighttext=(EditText)findViewById(R.id.height);
        weighttext=(EditText)findViewById(R.id.weight);

        linearLayout=(LinearLayout)findViewById(R.id.bmidetails);
        linearLayout.setVisibility(View.INVISIBLE);

        iv=(ImageView)findViewById(R.id.bmiscale);
        agetv=(TextView)findViewById(R.id.agetextview);
        nametv=(TextView)findViewById(R.id.name);
        bmitv=(TextView)findViewById(R.id.bmi);
        bmidesctv=(TextView) findViewById(R.id.bmidesc);
        heighttv=(TextView)findViewById(R.id.heighttextview);
        weighttv=(TextView)findViewById(R.id.weighttextview);
        agetv.setVisibility(View.INVISIBLE);
        heighttv.setVisibility(View.INVISIBLE);
        weighttv.setVisibility(View.INVISIBLE);

        btngo=(Button)findViewById(R.id.gobtn);
        btnsubmit=(Button)findViewById(R.id.btn_submit);

        nametv.setText("HI "+FDBAccess.data.child("Name").getValue(String.class));
        View.OnClickListener listener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.gobtn:
                    Intent i=new Intent(getBaseContext(),UserDetails2.class);
                    startActivity(i);
                    break;
                case R.id.btn_submit:
                    Log.d("SUBMIT BUTTON","PRESSED");
                    calcbmi();
                    addData();
                    agetv.setVisibility(View.VISIBLE);
                    heighttv.setVisibility(View.VISIBLE);
                    weighttv.setVisibility(View.VISIBLE);
                    agetext.setVisibility(View.INVISIBLE);
                    heighttext.setVisibility(View.INVISIBLE);
                    weighttext.setVisibility(View.INVISIBLE);
                    btnsubmit.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
            }
        }
    };
    btnsubmit.setOnClickListener(listener);
    btngo.setOnClickListener(listener);
}

    private void addData() {

        FDBAccess.mref.child("Details").child("Age").setValue(age);
        FDBAccess.mref.child("Details").child("Height").setValue(height);
        FDBAccess.mref.child("Details").child("Weight").setValue(weight);
        FDBAccess.mref.child("Details").child("BMI").setValue(bmi);
    }

    private void calcbmi() {
        age=Integer.parseInt(agetext.getText().toString());
        height=Integer.parseInt(heighttext.getText().toString());
        weight=Integer.parseInt(weighttext.getText().toString());
        agetv.setText(String.valueOf(age));
        heighttv.setText(String.valueOf(height));
        weighttv.setText(String.valueOf(weight));
        String text;
        bmi=1.0*weight/((height*height)/10000.0);
        DecimalFormat f = new DecimalFormat("##.00");
        bmi=Double.parseDouble(f.format(bmi));

        bmitv.setText("BMI: "+String.valueOf(bmi));
        if(bmi<18.5)
        {
            double diff= 18.5-bmi;
            double weight=diff*(height*height)/10000;
            text="BMI is below normal range, You are underweight!"+ "\nYou need to gain "+String.valueOf(weight)+" kg to reach normal bmi range";
        }
        else if(bmi>25.0 && bmi<30)
        {
            double diff= bmi-25.0;
            double weight=diff*(height*height)/10000;
            text="BMI is above normal range, You are overweight!"+"\nYou need to lose "+String.valueOf(weight)+" kg to reach normal bmi range";

        }
        else if(bmi>30 )
        {
            double diff= bmi-25.0;
            double weight=diff*(height*height)/10000;
            text="BMI is way above normal range, You are obese!"+"\nYou need to lose "+String.valueOf(weight)+" kg to reach normal bmi range";
        }
        else
        {
            text="BMI is in healthy range, You are fit! Keep monitoring yourself to keep it that way";
        }
        bmidesctv.setText(text);
    }

}
