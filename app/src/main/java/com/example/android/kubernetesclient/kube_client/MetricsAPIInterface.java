package com.example.android.kubernetesclient.kube_client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MetricsAPIInterface {
    @GET("/metrics")
    Call<MetricsResponse> getMetrics(@Query("resourceName") String resourceName,
                                                @Query("startTimestamp") String startTimestamp,
                                                @Query("endTimestamp") String endTimestamp);
}
