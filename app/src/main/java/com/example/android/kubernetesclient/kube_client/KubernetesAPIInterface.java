package com.example.android.kubernetesclient.kube_client;


import retrofit2.Call;
import retrofit2.http.GET;

public interface KubernetesAPIInterface {
    @GET("/api/v1/pods")
    Call<KubernetesResourceResponse> getPods();

    @GET("/api/v1/services")
    Call<KubernetesResourceResponse> getServices();
}