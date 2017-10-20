package com.example.huzaifa.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUp extends AppCompatActivity {
    private static final String TAG = SignUp.class.getSimpleName();
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    EditText email, password,username;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        username=(EditText)findViewById(R.id.username);
        email.setHint("Enter email");
        password.setHint("Enter Password");
        btn = (Button) findViewById(R.id.btn_create);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em=email.getText().toString();
                String pw=password.getText().toString();
                if(TextUtils.isEmpty(em)|| TextUtils.isEmpty(pw))
                    Toast.makeText(getBaseContext(),"Email or password cannot be empty!",Toast.LENGTH_SHORT).show();
                else
                    signup(em,pw);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    public void signup(String email,String password)
    {
        Log.d(TAG, "createAccount:" + email);


        //showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Failed=" + task.getException().getMessage());
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            String uid=user.getUid();
                            Firebase mref=new Firebase("https://myapplication-75bf5.firebaseio.com/");
                            mref.child("Users").child(uid).child("Name").setValue(username.getText().toString());
                            mref.child("Users").child(uid).child("Steps").setValue(0);
                            Intent i= new Intent(getBaseContext(),MainActivity.class);
                            startActivity(i);
                        }


                        // ...
                    }
                });

    }
}
