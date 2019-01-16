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

    public void getMetrics(String resourceName, long startTimestamp, long endTimestamp,
                           Callback<MetricsResponse> callback) {
        Call<MetricsResponse> call = this.service.getMetrics(resourceName,
                Long.toString(startTimestamp), Long.toString(endTimestamp));

        Log.d("Item", "Pornesc call catre metrics");
        call.enqueue(callback);
    }

}
