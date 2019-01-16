package com.example.android.kubernetesclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.android.kubernetesclient.adapters.ServicesAdapter;
import com.example.android.kubernetesclient.kube_client.KubernetesClient;

import java.util.ArrayList;

public class ServicesActivity extends AppCompatActivity {

    String namespace;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        gridLayoutManager = new GridLayoutManager(this, 3);
        namespace = loadNamespace();
        loadServices();
    }

    public String loadNamespace() {
        return getIntent().getStringExtra("namespace");
    }

    public void loadServices() {
        RecyclerView servicesRecyclerGridView = findViewById(R.id.services_recyclerview);
        servicesRecyclerGridView.setAdapter(new ServicesAdapter(new ArrayList<>()));
        servicesRecyclerGridView.setLayoutManager(gridLayoutManager);

        TextView emptyView = findViewById(R.id.services_empty_text_view);
        new KubernetesClient().getServices(servicesRecyclerGridView, namespace, emptyView);
    }

}
