package com.example.android.kubernetesclient.utils;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.kubernetesclient.chart_formatters.PodXChartFormatter;
import com.example.android.kubernetesclient.kube_client.MetricsResponse;
import com.example.android.kubernetesclient.models.Metric;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartUtils {

    public static void styleLeftAxisChart(LineChart chart, String metricsType) {
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        Log.d("styling", metricsType);
        if (metricsType.equals("CPU")) {
            leftAxis.setAxisMaximum(1);
        } else {
            leftAxis.setAxisMaximum(4);
        }
        leftAxis.setAxisMinimum(0);
        leftAxis.setTextSize(15);
    }

    public static void styleChart(LineChart chart, String metricsType) {
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

        ChartUtils.styleLeftAxisChart(chart, metricsType);
    }

    public static void styleDataSet(LineDataSet dataSet) {
        int chartColor = Color.rgb(41,126, 124);
        dataSet.setColor(chartColor);
        dataSet.setLineWidth(2f);
        dataSet.setFillAlpha(255);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(chartColor);
        dataSet.setDrawCircleHole(false);
        dataSet.setHighlightEnabled(false);
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(false);
        dataSet.setValueTextSize(20);
    }

    public static void updateChartData(List<Metric> metrics, LineChart lineChart, String metricsType) {
        Log.d("Reincarc graficul", "");
        List<Entry> dataEntries = ChartUtils.metricsToEntries(metrics, metricsType);

        LineDataSet dataSet = new LineDataSet(dataEntries, metricsType + " usage");
        ChartUtils.styleDataSet(dataSet);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
        lineChart.notifyDataSetChanged();
    }

    private static List<Entry> metricsToEntries(List<Metric> metrics, String metricsType) {
        List<Entry> entries = new ArrayList<>();
        if (metricsType.equals("CPU")) {
            for (Metric metric : metrics) {
                entries.add(new Entry(metric.getTimestamp() * 1000.0f, metric.getCpuValue()));
            }
        } else {
            for (Metric metric : metrics) {
                entries.add(new Entry(metric.getTimestamp() * 1000.0f, metric.getMemoryValue()));
            }
        }

        return entries;
    }

    public static Callback<MetricsResponse> callbackToPopulateMetricsInUI(LineChart chart,
                                                                    String metricsType) {
        return new Callback<MetricsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MetricsResponse> call,
                                   @NonNull Response<MetricsResponse> response) {

                List<Metric> metrics = new ArrayList<>();
                if (response.body() != null) {
                    for (MetricsResponse.MetricResponse item : response.body().getMetrics()) {
                        Metric metric = new Metric(item.getMemoryValue(), item.getCpuValue(),
                                item.getTimestamp(), item.getResourceName());
                        metrics.add(metric);
                    }
                }
                Log.d("Metricile", metrics.toString());
                if (chart != null) {
                    ChartUtils.updateChartData(metrics, chart, metricsType);
                    ChartUtils.styleLeftAxisChart(chart, metricsType);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MetricsResponse> call, @NonNull Throwable t) {
                Log.e("Error getting metrics :", t.toString());
            }
        };
    }
}
