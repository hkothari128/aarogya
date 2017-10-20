package com.example.huzaifa.myapplication;

import android.widget.ProgressBar;

/**
 * Created by huzaifa on 04-Mar-17.
 */

public class DataModel {

    int steps;
    long date;
    int water;
    public DataModel(int steps,long date,int water) {
        this.steps=steps;
        this.date=date;
        this.water=water;
    }

    public int getSteps() {
        return steps;
    }
    public long getDate() {
        return date;
    }
    public int getWater() {return water;}



}
