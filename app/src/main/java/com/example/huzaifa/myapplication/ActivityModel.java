package com.example.huzaifa.myapplication;

/**
 * Created by huzaifa on 23-Mar-17.
 */

public class ActivityModel {
    String name;
    long time;
    int duration;
    float calorie;

    public ActivityModel(){}
    public ActivityModel(String type,String name,Long time,float calorie)
    {
        this.name=name;
        this.time=time;
        this.calorie=calorie;
        this.duration=-1;
    }
    public ActivityModel(String type,String name,Long time,float calorie,int duration)
    {
        this.name=name;
        this.time=time;
        this.calorie=calorie;
        this.duration=duration;
    }

    public String getName() {
        return name;
    }
    public long getTime() {
        return time;
    }
    public float getCalorie() {return calorie;}
    public int getDuration(){return duration;}
}
