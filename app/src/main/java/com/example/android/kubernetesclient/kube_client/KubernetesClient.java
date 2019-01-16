package com.example.android.kubernetesclient.kube_client;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KubernetesClient {
    KubernetesAPIInterface service;

    public KubernetesClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.89.30.67:8002")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(KubernetesAPIInterface.class);
    }

    public void getPods(Callback<KubernetesResourceResponse> callback) {
        Call<KubernetesResourceResponse> call = this.service.getPods();
        Log.d("Item", "Pornesc call catre pods");
        call.enqueue(callback);
    }

    public void getServices(Callback<KubernetesResourceResponse> callback) {
        Call<KubernetesResourceResponse> call = this.service.getServices();
        Log.d("Item", "Pornesc call catre services");
        call.enqueue(callback);
    }

    public void getNodes(Callback<KubernetesResourceResponse> callback) {
        Call<KubernetesResourceResponse> call = this.service.getNodes();
        Log.d("Item", "Pornesc call catre nodes");
        call.enqueue(callback);
    }

    public void getNamespaces(Callback<KubernetesResourceResponse> callback) {
        Call<KubernetesResourceResponse> call = this.service.getNamespaces();
        Log.d("Item", "Pornesc call catre namespaces");
        call.enqueue(callback);
    }
}
