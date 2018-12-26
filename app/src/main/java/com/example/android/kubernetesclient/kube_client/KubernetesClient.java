package com.example.android.kubernetesclient.kube_client;

import android.util.Log;
import android.widget.GridView;

import com.example.android.kubernetesclient.adapters.PodsAdapter;
import com.example.android.kubernetesclient.models.Pod;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KubernetesClient {
    KubernetesAPIInterface service;

    public KubernetesClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.89.110.74:8001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(KubernetesAPIInterface.class);
    }

    public void getPods(final GridView podsGridView) {
        Call<KubernetesResourceResponse> call = this.service.listPods();
        Log.d("Item", "Pornesc call");
        call.enqueue(new Callback<KubernetesResourceResponse>() {
            @Override
            public void onResponse(Call<KubernetesResourceResponse> call,
                                   Response<KubernetesResourceResponse> response) {

                List<Pod> pods = new ArrayList<>();
                if (response.body() != null) {
                    for (KubernetesResourceResponse.KubeResource item : response.body().getItems()) {
                        Pod pod = new Pod(item.getMetadata().getName(), item.getMetadata().getNamespace(), item.getMetadata().getUid(),
                                item.getMetadata().getCreationTimestamp(), item.status.getStatus());
                        pods.add(pod);
                    }
                }


                if (!pods.isEmpty()) {
                    PodsAdapter adapter = (PodsAdapter) podsGridView.getAdapter();
                    adapter.setPods(pods);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<KubernetesResourceResponse> call, Throwable t) {

            }
        });
    }
}
