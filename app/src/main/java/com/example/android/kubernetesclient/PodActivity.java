package com.example.android.kubernetesclient;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.kubernetesclient.chart_formatters.PodXChartFormatter;
import com.example.android.kubernetesclient.models.Pod;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class PodActivity extends AppCompatActivity {

    public String[] dropdownOptions = {"CPU", "Memory"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod);
        Pod pod = loadPod();
        displayPodData(pod);
        displayPodMetrics("1", "CPU");
        setDataForDropdown(pod);
    }

    public void setDataForDropdown(final Pod pod) {
        Spinner metricsSpinner = findViewById(R.id.pod_metrics_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, this.dropdownOptions);
        metricsSpinner.setAdapter(adapter);
        metricsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadMetricsAndUpdateChart(dropdownOptions[position], pod.getUid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    public Pod loadPod() {
        return (Pod)getIntent().getSerializableExtra("pod");
    }

    public void loadMetricsAndUpdateChart(String metricsType, String podId) {
        Log.d("Reincarc graficul", metricsType);
        LineChart chart = findViewById(R.id.pod_chart);
        List<Entry> entries = loadDataForGraph(metricsType, podId);

        LineDataSet dataSet = new LineDataSet(entries, metricsType + " usage");
        styleDataSet(dataSet);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
        chart.notifyDataSetChanged();
    }

    public void displayPodData(Pod pod) {
        TextView podNameView = findViewById(R.id.pod_name);
        podNameView.setText(pod.getName());

        TextView podNamespaceView = findViewById(R.id.pod_namespace);
        podNamespaceView.setText(pod.getNamespace());

        TextView podCreatedAtView = findViewById(R.id.pod_createdat);
        String date = pod.getCreatedTimestamp().toString();

        podCreatedAtView.setText(date.substring(0, date.length()-2));

        ImageView podStatusView = findViewById(R.id.pod_status);
        int podImageResourceId;
        if (pod.isRunning()) {
            podImageResourceId = R.drawable.success;
        } else {
            podImageResourceId = R.drawable.error;
        }
        podStatusView.setImageResource(podImageResourceId);
    }

    public void displayPodMetrics(String podId, String metricsType) {
        LineChart chart = findViewById(R.id.pod_chart);
        styleChart(chart);

        List<Entry> entries = loadDataForGraph(metricsType, podId);
        LineDataSet dataSet = new LineDataSet(entries, "CPU usage");
        styleDataSet(dataSet);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
    }

    public void styleChart(LineChart chart) {
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

    public void styleDataSet(LineDataSet dataSet) {
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

    public List<Entry> loadDataForGraph(String metricsType, String podId) {
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
