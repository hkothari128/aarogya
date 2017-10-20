package com.example.huzaifa.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by huzaifa on 04-Mar-17.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> {

    private ArrayList<DataModel> dataSet;
    private Context mContext;
    LayoutInflater inflater;
    // View lookup cache


    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.steps, data);
        this.dataSet = data;
        this.mContext=context;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(R.layout.steps, null, true);
        TextView step = (TextView) listViewItem.findViewById(R.id.step);
        TextView date = (TextView) listViewItem.findViewById(R.id.date);
        ProgressBar pb=(ProgressBar)listViewItem.findViewById(R.id.pbar);
        TextView water=(TextView)listViewItem.findViewById(R.id.water);
        SimpleDateFormat formatter = new SimpleDateFormat("E MMMM dd, yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dataSet.get(position).getDate());
        date.setText(formatter.format(calendar.getTime()));
        step.setText(String.valueOf(dataSet.get(position).getSteps()));
        water.setText(String.valueOf(dataSet.get(position).getWater())+"ml");
        pb.setMax(FDBAccess.data.child("MaxStep").getValue(int.class));
        pb.setProgress(dataSet.get(position).getSteps());


        return  listViewItem;
    }

}
