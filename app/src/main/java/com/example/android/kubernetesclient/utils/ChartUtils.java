package com.example.android.kubernetesclient.utils;

import android.graphics.Color;

import com.example.android.kubernetesclient.chart_formatters.PodXChartFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChartUtils {

    public static void styleChart(LineChart chart) {
        chart.setDoubleTapToZoomEnabled(false);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setAutoScaleMinMaxEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new PodXChartFormatter());
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(15);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(10);
        leftAxis.setTextSize(15);
    }

    public static void styleDataSet(LineDataSet dataSet) {
        int chartColor = Color.rgb(41,126, 124);
        dataSet.setColor(chartColor);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(5f);
        dataSet.setFillAlpha(255);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(chartColor);
        dataSet.setDrawCircleHole(false);
        dataSet.setHighlightEnabled(false);
        dataSet.setDrawValues(false);
        dataSet.setValueTextSize(20);
    }

    public static List<Entry> loadDataForGraph(String metricsType, String podId) {
        List<Entry> data = new ArrayList<>();
        if (metricsType.equals("CPU")) {
            data.add(new Entry(1506002962000.0f, 5.0f));
            data.add(new Entry(1516002962000.0f, 4.0f));
            data.add(new Entry(1526002962000.0f, 3.0f));
            data.add(new Entry(1536002962000.0f, 2.0f));
            data.add(new Entry(1546002962000.0f, 1.0f));
            data.add(new Entry(1556002962000.0f, 4.0f));
            data.add(new Entry(1566002962000.0f, 5.0f));
        } else {
            data.add(new Entry(1506002962000.0f, 8.0f));
            data.add(new Entry(1516002962000.0f, 4.0f));
            data.add(new Entry(1526002962000.0f, 7.0f));
            data.add(new Entry(1536002962000.0f, 10.0f));
            data.add(new Entry(1546002962000.0f, 4.0f));
            data.add(new Entry(1556002962000.0f, 8.0f));
            data.add(new Entry(1566002962000.0f, 1.0f));
        }
        return data;
    }
}
