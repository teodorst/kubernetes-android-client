package com.example.android.kubernetesclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.kubernetesclient.adapters.PodsAdapter;
import com.example.android.kubernetesclient.kube_client.KubernetesClient;
import com.example.android.kubernetesclient.kube_client.KubernetesResourceResponse;
import com.example.android.kubernetesclient.models.Pod;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PodsActivity extends AppCompatActivity {

    String namespace;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pods);

        gridLayoutManager = new GridLayoutManager(this, 3);
        namespace = loadNamespace();
        Log.d("Namespace:", namespace);
        loadPods();
    }

    public String loadNamespace() {
        return getIntent().getStringExtra("namespace");
    }

    public void loadPods() {
        RecyclerView podsRecyclerGridView = findViewById(R.id.pod_recyclerview);
        podsRecyclerGridView.setAdapter(new PodsAdapter(new ArrayList<>()));
        podsRecyclerGridView.setLayoutManager(gridLayoutManager);
        TextView podsEmptyView = findViewById(R.id.pods_empty_text_view);
        new KubernetesClient().getPods(new Callback<KubernetesResourceResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<KubernetesResourceResponse> call,
                                   @NonNull Response<KubernetesResourceResponse> response) {

                List<Pod> pods = new ArrayList<>();
                if (response.body() != null) {
                    for (KubernetesResourceResponse.KubeResource item : response.body().getItems()) {
                        Pod pod = new Pod(item.getMetadata().getName(), item.getMetadata().getNamespace(), item.getMetadata().getUid(),
                                item.getMetadata().getCreationTimestamp(), item.getStatus().getPhase());
                        pods.add(pod);
                    }
                }

                if (!namespace.equals("All")) {
                    pods = Lists.newArrayList(Collections2.filter(
                            pods, p -> p.getNamespace().equals(namespace)));
                }

                if (!pods.isEmpty()) {
                    PodsAdapter adapter = (PodsAdapter) podsRecyclerGridView.getAdapter();
                    adapter.setPods(pods);
                    adapter.notifyDataSetChanged();
                    podsEmptyView.setVisibility(View.GONE);
                    podsRecyclerGridView.setVisibility(View.VISIBLE);
                } else {

                    podsEmptyView.setText("Couldn't load pods. Try again later!");
                    podsRecyclerGridView.setVisibility(View.GONE);
                    podsEmptyView.setVisibility(View.VISIBLE);
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<KubernetesResourceResponse> call, @NonNull Throwable t) {
                Log.e("Error getting pods:", t.toString());
                podsEmptyView.setText("Couldn't load pods. Try again later!");
                podsEmptyView.setVisibility(View.VISIBLE);
                podsRecyclerGridView.setVisibility(View.GONE);
            }
        });
    }
}
