package com.example.android.kubernetesclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.kubernetesclient.models.Node;
import com.example.android.kubernetesclient.utils.ChartUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.List;

public class NodeActivity extends AppCompatActivity {

    public String[] dropdownOptions = {"CPU", "Memory"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);

        Node node = loadNode();
        displayNodeData(node);
        displayNodeMetrics(node.getUid(), dropdownOptions[0]);
    }

    public Node loadNode() {
        return (Node)getIntent().getSerializableExtra("node");
    }

    @SuppressLint("DefaultLocale")
    public void displayNodeData(Node node) {
        TextView podNameView = findViewById(R.id.node_name);
        podNameView.setText(node.getName());

        TextView nodeOsView = findViewById(R.id.node_os);
        nodeOsView.setText(node.getOS());

        TextView nodeCpusView = findViewById(R.id.node_cpus);
        nodeCpusView.setText(String.format("%d", node.getCpuNo()));

        TextView nodeMemoryView = findViewById(R.id.node_memory);
        nodeMemoryView.setText(String.format("%.2f GB", node.getMemory()));

        setDataForDropdown(node);
    }

    public void setDataForDropdown(final Node node) {
        Spinner metricsSpinner = findViewById(R.id.node_metrics_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, this.dropdownOptions);
        metricsSpinner.setAdapter(adapter);
        metricsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadMetricsAndUpdateChart(dropdownOptions[position], node.getUid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    public void loadMetricsAndUpdateChart(String metricsType, String nodeId) {
        Log.d("Reincarc graficul", metricsType);
        LineChart chart = findViewById(R.id.node_chart);
        List<Entry> entries = ChartUtils.loadDataForGraph(metricsType, nodeId);

        LineDataSet dataSet = new LineDataSet(entries, metricsType + " usage");
        ChartUtils.styleDataSet(dataSet);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
        chart.notifyDataSetChanged();
    }

    public void displayNodeMetrics(String nodeId, String metricsType) {
        LineChart chart = findViewById(R.id.node_chart);
        ChartUtils.styleChart(chart);

        List<Entry> entries = ChartUtils.loadDataForGraph(metricsType, nodeId);
        LineDataSet dataSet = new LineDataSet(entries, "CPU usage");
        ChartUtils.styleDataSet(dataSet);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
    }
}
