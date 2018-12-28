package com.example.android.kubernetesclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.kubernetesclient.adapters.NodesAdapter;
import com.example.android.kubernetesclient.adapters.PodsAdapter;
import com.example.android.kubernetesclient.kube_client.KubernetesClient;
import com.example.android.kubernetesclient.models.Node;
import com.example.android.kubernetesclient.models.Pod;

public class NodesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodes);

        loadNodes();
        setGridListener();
    }

    public void loadNodes() {
        GridView nodesGridView = findViewById(R.id.nodes_gridview);
        nodesGridView.setAdapter(new NodesAdapter(this));
        new KubernetesClient().getNodes(nodesGridView);
    }

    public void setGridListener() {
        final GridView nodesGridView = findViewById(R.id.nodes_gridview);
        nodesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Node clickedNode = (Node)nodesGridView.getAdapter().getItem(position);
                Intent intent = new Intent(getBaseContext(), NodeActivity.class);
                intent.putExtra("node", clickedNode);
                startActivity(intent);
            }
        });
    }
}
