package com.example.huzaifa.myapplication;

import android.app.Application;
import com.firebase.client.Firebase;
/**
 * Created by huzaifa on 15-Feb-17.
 */

public class myapplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
