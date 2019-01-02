package com.example.android.kubernetesclient.kube_client;

import android.util.Log;

import com.example.android.kubernetesclient.models.Metric;
import com.example.android.kubernetesclient.utils.ChartUtils;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MetricsClient {
    MetricsAPIInterface service;

    public MetricsClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://157.230.96.173:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(MetricsAPIInterface.class);
    }

    public void getMetrics(String resourceName, long startTimestamp, long endTimestamp, final LineChart lineChart, final String metricsType) {
        Call<MetricsResponse> call = this.service.getMetrics(resourceName,
                Long.toString(startTimestamp), Long.toString(endTimestamp));

        Log.d("Item", "Pornesc call catre metrics");
        call.enqueue(new Callback<MetricsResponse>() {
            @Override
            public void onResponse(Call<MetricsResponse> call,
                                   Response<MetricsResponse> response) {

                List<Metric> metrics = new ArrayList<>();
                if (response.body() != null) {
                    for (MetricsResponse.MetricResponse item : response.body().getMetrics()) {
                        Metric metric = new Metric(item.getMemoryValue(), item.getCpuValue(), item.getTimestamp(), item.getResourceName());
                        metrics.add(metric);
                    }
                }
                Log.d("Metricile", metrics.toString());
                if (lineChart != null) {
                    ChartUtils.updateChartData(metrics, lineChart, metricsType);
                    ChartUtils.styleLeftAxisChart(lineChart, metricsType);
                }
            }

            @Override
            public void onFailure(Call<MetricsResponse> call, Throwable t) {
                Log.e("Error getting pods:", t.toString());
            }
        });
    }

}
