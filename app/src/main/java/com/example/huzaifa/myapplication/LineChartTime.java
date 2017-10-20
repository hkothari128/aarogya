package com.example.huzaifa.myapplication;

/**
 * Created by huzaifa on 27-Mar-17.
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.GenericTypeIndicator;
//import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;
import android.support.v7.widget.Toolbar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LineChartTime extends AppCompatActivity {

    private LineChart lineChart;
    private LineChart lineChart2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.drawable.logo);

        toolbar.setTitle(R.string.app_name);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        lineChart = (LineChart) findViewById(R.id.chart1);
        lineChart2 = (LineChart) findViewById(R.id.chart2);

        GenericTypeIndicator<ArrayList<Long>> datetype = new GenericTypeIndicator<ArrayList<Long>>() {
        };
        ArrayList<Long>datelist = FDBAccess.data.child("StepHistory").child("Datelist").getValue(datetype);
        GenericTypeIndicator<ArrayList<Float>> caltype = new GenericTypeIndicator<ArrayList<Float>>() {
        };
        ArrayList<Float>cal_out=FDBAccess.data.child("StepHistory").child("Calorie_out_list").getValue(caltype);
        ArrayList<Float>cal_in=FDBAccess.data.child("StepHistory").child("Calorie_in_list").getValue(caltype);
        GenericTypeIndicator<ArrayList<Integer>> steptype = new GenericTypeIndicator<ArrayList<Integer>>() {
        };
        ArrayList<Integer>steplist = FDBAccess.data.child("StepHistory").child("Steplist").getValue(steptype);
        ArrayList<Integer>waterlist = FDBAccess.data.child("StepHistory").child("Waterlist").getValue(steptype);
        final SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM");

        List<Entry> valsComp1 = new ArrayList<Entry>();
        List<Entry> valsComp2 = new ArrayList<Entry>();

        for(int i=datelist.size()-1;i>=0;i--) {
            Entry c1e1 = new Entry(datelist.get(i), cal_out.get(i)); // 0 == quarter 1
            valsComp1.add(c1e1);
            Entry c2e1 = new Entry(datelist.get(i), cal_in.get(i)); // 0 == quarter 1
            valsComp2.add(c2e1);
        }


        LineDataSet setComp1 = new LineDataSet(valsComp1, "Calories out");
        setComp1.setAxisDependency(AxisDependency.LEFT);
        LineDataSet setComp2 = new LineDataSet(valsComp2, "Calories in");
        setComp2.setAxisDependency(AxisDependency.RIGHT);
        setComp1.setColor(ColorTemplate.rgb("#ff0000"));
        setComp1.setCircleColor(ColorTemplate.rgb("#ff0000"));
        setComp1.setCircleHoleRadius(10f);
        setComp2.setColor(ColorTemplate.rgb("#00ff00"));
        setComp2.setCircleColor(ColorTemplate.rgb("#00ff00"));
        setComp2.setCircleHoleRadius(10f);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setComp1);
        //
        dataSets.add(setComp2);
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dateformatter.format(value);
            }

        };
        LineData data = new LineData(dataSets);
        Description description=new Description();
        description.setText("Calorie Graph");

        lineChart.setDescription(description);
        lineChart.setData(data);
        lineChart.invalidate();
        lineChart.animateX(300);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(2f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);


    //BAR CHART


        ArrayList<Entry> valueSet1 = new ArrayList<>();
        ArrayList<Entry> valueSet2 = new ArrayList<>();


        for(int i=datelist.size()-1;i>=0;i--) {
            Entry v1e1 = new BarEntry(datelist.get(i), waterlist.get(i));
            valueSet1.add(v1e1);
            Entry v2e1 = new BarEntry(datelist.get(i), steplist.get(i)); // Jan
            valueSet2.add(v2e1);

        }

        LineDataSet set1 = new LineDataSet(valueSet1, "Water");
        setComp1.setAxisDependency(AxisDependency.LEFT);
        LineDataSet set2 = new LineDataSet(valueSet2, "Steps");
        setComp2.setAxisDependency(AxisDependency.RIGHT);
        setComp1.setColor(ColorTemplate.rgb("#ff0000"));
        setComp1.setCircleColor(ColorTemplate.rgb("#ff0000"));
        setComp1.setCircleHoleRadius(10f);
        setComp2.setColor(ColorTemplate.rgb("#00ff00"));
        setComp2.setCircleColor(ColorTemplate.rgb("#00ff00"));
        setComp2.setCircleHoleRadius(10f);

        LineDataSet lds1 = new LineDataSet(valueSet1, "Brand 1");
        lds1.setColor(Color.rgb(0, 155, 0));
        LineDataSet lds2 = new LineDataSet(valueSet2, "Brand 2");
        lds2.setColor(Color.BLACK);

        ArrayList<ILineDataSet> ldsets = new ArrayList<ILineDataSet>();
        ldsets.add(lds1);
        ldsets.add(lds2);
        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 3f; // x2 dataset
        LineData linedata = new LineData(ldsets);


        linedata.setValueTypeface(Typeface.DEFAULT);
        lineChart2.setData(linedata);
         Description description1=new Description();
        description1.setText("waterstep graph");
        lineChart2.setDescription(description1);
        //barChart.groupBars(0,groupSpace,barSpace);
        lineChart2.animateXY(2000, 2000);
        //barChart.invalidate();

        XAxis barxAxis =  lineChart2.getXAxis();
        barxAxis.setGranularity(2f);
        barxAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        barxAxis.setValueFormatter(formatter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


}