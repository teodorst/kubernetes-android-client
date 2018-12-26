package com.example.android.kubernetesclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.kubernetesclient.adapters.PodsAdapter;
import com.example.android.kubernetesclient.kube_client.KubernetesClient;
import com.example.android.kubernetesclient.kube_client.KubernetesResourceResponse;
import com.example.android.kubernetesclient.models.Pod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PodsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pods);

        loadPods();
        setGridListener();
    }

    public void loadPods() {
        GridView podsGridView = findViewById(R.id.pod_gridview);
        podsGridView.setAdapter(new PodsAdapter(this));
        new KubernetesClient().getPods(podsGridView);
    }

    public void setGridListener() {
        final GridView podsGridView = findViewById(R.id.pod_gridview);
        podsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pod clickedPod = (Pod)podsGridView.getAdapter().getItem(position);
                Intent intent = new Intent(getBaseContext(), PodActivity.class);
                intent.putExtra("pod", clickedPod);
                startActivity(intent);
            }
        });
    }
}
