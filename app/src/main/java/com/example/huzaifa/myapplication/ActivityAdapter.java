package com.example.huzaifa.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by huzaifa on 23-Mar-17.
 */

public class ActivityAdapter extends ArrayAdapter<ActivityModel> {
    private ArrayList<ActivityModel> dataSet;
    private Context mContext;
    LayoutInflater inflater;

    public ActivityAdapter(ArrayList<ActivityModel> data, Context context) {
        super(context, R.layout.activityformat, data);
        this.dataSet = data;
        this.mContext=context;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(R.layout.activityformat, null, true);
        TextView type=(TextView) listViewItem.findViewById(R.id.type);
        TextView name = (TextView) listViewItem.findViewById(R.id.name);
        TextView time = (TextView) listViewItem.findViewById(R.id.time);
        TextView calorie=(TextView)listViewItem.findViewById(R.id.calorie);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dataSet.get(position).getTime());
        time.setText("At: "+formatter.format(calendar.getTime()));
        type.setText("FOOD ITEM");
        String calorie_type="Calories Consumed: ";
        String nametext=dataSet.get(position).getName();
        int duration=dataSet.get(position).getDuration();
        if(duration!=-1)
        {
            type.setText("ACTIVITY");
            calorie_type="Calories burnt: ";
            int hours=duration/60;
            int minutes=duration%60;
            nametext+="\nDuration: "+hours+" hr "+minutes+" min";
        }

        name.setText("Name: "+nametext);
        calorie.setText(calorie_type+String.valueOf(dataSet.get(position).getCalorie()));
        return  listViewItem;
    }
}
