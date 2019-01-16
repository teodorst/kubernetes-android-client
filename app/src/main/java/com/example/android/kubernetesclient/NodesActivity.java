package com.example.android.kubernetesclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.kubernetesclient.adapters.NodesAdapter;
import com.example.android.kubernetesclient.kube_client.KubernetesClient;

import java.util.ArrayList;

public class NodesActivity extends AppCompatActivity {

    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodes);

        gridLayoutManager = new GridLayoutManager(this, 3);
        loadNodes();
    }

    public void loadNodes() {
        RecyclerView nodesRecyclerGridView = findViewById(R.id.nodes_recyclerview);
        nodesRecyclerGridView.setAdapter(new NodesAdapter(new ArrayList<>()));
        nodesRecyclerGridView.setLayoutManager(gridLayoutManager);
        new KubernetesClient().getNodes(nodesRecyclerGridView);
    }
}
