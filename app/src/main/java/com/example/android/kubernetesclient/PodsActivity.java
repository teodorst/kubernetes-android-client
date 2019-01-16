package com.example.android.kubernetesclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.android.kubernetesclient.adapters.PodsAdapter;
import com.example.android.kubernetesclient.kube_client.KubernetesClient;

import java.util.ArrayList;

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
        new KubernetesClient().getPods(podsRecyclerGridView, namespace, podsEmptyView);
    }
}
