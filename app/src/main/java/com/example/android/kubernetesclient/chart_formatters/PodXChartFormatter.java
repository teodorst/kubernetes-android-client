package com.example.android.kubernetesclient.chart_formatters;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.sql.Timestamp;
import java.util.Date;

public class PodXChartFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        // write your logic here
        Log.d("Mergem cu ", "" + value);
        Timestamp ts=new Timestamp((long)value);
        Date date=new Date(ts.getTime());
        String day = ""+date.getDay();
        Log.d("Convertesc date", " "+value+ " to " + day);
        Log.d("Convertesc date", date.toString());
        return "" + day + "/"+ (date.getMonth()+1);
    }

    /** this is only needed if numbers are returned, else return 0 */
    @Override
    public int getDecimalDigits() { return 0; }

}
