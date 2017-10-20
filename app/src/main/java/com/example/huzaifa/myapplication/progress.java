package com.example.huzaifa.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.GenericTypeIndicator;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class progress extends AppCompatActivity {
    GraphView graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        graph=(GraphView)findViewById(R.id.graph);
        GenericTypeIndicator<ArrayList<Long>> datetype = new GenericTypeIndicator<ArrayList<Long>>() {
        };
        ArrayList<Long>datelist = FDBAccess.data.child("StepHistory").child("Datelist").getValue(datetype);
        GenericTypeIndicator<ArrayList<Float>> caltype = new GenericTypeIndicator<ArrayList<Float>>() {
        };
        ArrayList<Float>cal_out=FDBAccess.data.child("StepHistory").child("Calorie_out_list").getValue(caltype);
        ArrayList<Float>cal_in=FDBAccess.data.child("StepHistory").child("Calorie_in_list").getValue(caltype);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Calendar calendar = Calendar.getInstance();

        DataPoint [] values=new DataPoint[datelist.size()];
        for(int i=datelist.size()-1;i>=0;i--) {
            values[datelist.size()-1-i]=new DataPoint(datelist.get(i), cal_out.get(i));
            Log.d("VALUES",String.valueOf(values[datelist.size()-i-1]));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(values);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10f);
        graph.addSeries(series);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(3);
        nf.setMinimumIntegerDigits(2);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return formatter.format(value);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        graph.getGridLabelRenderer().setNumHorizontalLabels(5);
        graph.getGridLabelRenderer().setTextSize(18);

        for(int i=datelist.size()-1;i>=0;i--)
            values[datelist.size()-1-i]=new DataPoint(datelist.get(i), cal_in.get(i));

            LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(values);
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10f);
// set second scale
        graph.getSecondScale().addSeries(series2);
// the y bounds are always manual for second scale
        graph.getSecondScale().setMinY(0);
        graph.getSecondScale().setMaxY(100);
        series.setTitle("calories out");
        series2.setTitle("calories in");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getLegendRenderer().setBackgroundColor(R.color.background);
        graph.getLegendRenderer().setPadding(20);
        series2.setColor(Color.RED);
        graph.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.RED);


        graph.getViewport().setXAxisBoundsManual(true);

        // enable scaling
        graph.getViewport().setScalable(true);
    }
}
