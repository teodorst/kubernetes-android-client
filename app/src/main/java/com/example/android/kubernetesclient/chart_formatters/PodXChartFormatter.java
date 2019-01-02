package com.example.android.kubernetesclient.chart_formatters;

import android.annotation.SuppressLint;
import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PodXChartFormatter implements IAxisValueFormatter {

    @SuppressLint("DefaultLocale")
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        // write your logic here
        Log.d("Mergem cu ", "" + value);
        Timestamp ts=new Timestamp((long)value);
        Date date = new Date(ts.getTime());
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return String.format("%d/%d", localDate.getDayOfMonth(), localDate.getMonthValue());
    }

    /** this is only needed if numbers are returned, else return 0 */
    @Override
    public int getDecimalDigits() { return 0; }

}
