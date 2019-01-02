package com.example.android.kubernetesclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.kubernetesclient.kube_client.MetricsClient;
import com.example.android.kubernetesclient.models.Pod;
import com.example.android.kubernetesclient.utils.ChartUtils;
import com.github.mikephil.charting.charts.LineChart;

public class PodActivity extends AppCompatActivity {

    public String[] dropdownOptions = {"CPU", "Memory"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod);
        Pod pod = loadPod();
        displayPodData(pod);
        displayChart("CPU");
        setDataForDropdown(pod);
    }

    public Pod loadPod() {
        return (Pod)getIntent().getSerializableExtra("pod");
    }

    public void setDataForDropdown(final Pod pod) {
        Spinner metricsSpinner = findViewById(R.id.pod_metrics_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, this.dropdownOptions);
        metricsSpinner.setAdapter(adapter);
        metricsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LineChart chart = findViewById(R.id.pod_chart);

                long endTimestamp = System.currentTimeMillis() / 1000;
                long currentHour = ((int)(endTimestamp / 3600)) * 3600;
                long startTimestamp = currentHour - 7 * 24 * 3600;
                new MetricsClient().getMetrics(pod.getName(), startTimestamp, endTimestamp, chart,
                        dropdownOptions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
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

    public void displayChart(String metricsType) {
        LineChart chart = findViewById(R.id.pod_chart);
        ChartUtils.styleChart(chart, metricsType);
    }

}
