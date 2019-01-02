package com.example.android.kubernetesclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.android.kubernetesclient.adapters.PodsAdapter;
import com.example.android.kubernetesclient.kube_client.KubernetesClient;
import com.example.android.kubernetesclient.kube_client.KubernetesResourceResponse;
import com.example.android.kubernetesclient.models.Pod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PodsActivity extends AppCompatActivity {

    String namespace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pods);

        namespace = loadNamespace();
        Log.d("Namespace:", namespace);
        loadPods();
        setGridListener();
    }

    public String loadNamespace() {
        return getIntent().getStringExtra("namespace");
    }

    public void loadPods() {
        GridView podsGridView = findViewById(R.id.pod_gridview);
        podsGridView.setAdapter(new PodsAdapter(this));
        TextView podsEmptyView = findViewById(R.id.pods_empty_text_view);
        new KubernetesClient().getPods(podsGridView, namespace, podsEmptyView);
    }

    public void setGridListener() {
        final GridView podsGridView = findViewById(R.id.pod_gridview);
        podsGridView.setOnItemClickListener((parent, view, position, id) -> {
            Pod clickedPod = (Pod)podsGridView.getAdapter().getItem(position);
            Intent intent = new Intent(getBaseContext(), PodActivity.class);
            intent.putExtra("pod", clickedPod);
            startActivity(intent);
        });
    }
}
