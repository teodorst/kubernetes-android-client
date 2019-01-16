package com.example.android.kubernetesclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.kubernetesclient.kube_client.MetricsClient;
import com.example.android.kubernetesclient.kube_client.MetricsResponse;
import com.example.android.kubernetesclient.models.Metric;
import com.example.android.kubernetesclient.models.Node;
import com.example.android.kubernetesclient.utils.ChartUtils;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NodeActivity extends AppCompatActivity {

    public String[] dropdownOptions = {"CPU", "Memory"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);

        Node node = loadNode();
        displayNodeData(node);
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

        LineChart chart = findViewById(R.id.node_chart);
        ChartUtils.styleChart(chart, dropdownOptions[0]);

        setDataForDropdown(node);
    }

    public void setDataForDropdown(final Node node) {
        Spinner metricsSpinner = findViewById(R.id.node_metrics_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, this.dropdownOptions);
        metricsSpinner.setAdapter(adapter);
        metricsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LineChart chart = findViewById(R.id.node_chart);

                long endTimestamp = System.currentTimeMillis() / 1000;
                long currentHour = ((int)(endTimestamp / 3600)) * 3600;
                long startTimestamp = currentHour - 7 * 24 * 3600;

                new MetricsClient().getMetrics(node.getName(), startTimestamp, endTimestamp,
                        ChartUtils.callbackToPopulateMetricsInUI(chart, dropdownOptions[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

        });
    }
}
