package com.example.android.kubernetesclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;

import com.example.android.kubernetesclient.adapters.ServicesAdapter;
import com.example.android.kubernetesclient.kube_client.KubernetesClient;
import com.example.android.kubernetesclient.models.Service;

public class ServicesActivity extends AppCompatActivity {

    String namespace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        namespace = loadNamespace();
        loadServices();
        setItemListener();
    }

    public String loadNamespace() {
        return getIntent().getStringExtra("namespace");
    }

    public void loadServices() {
        GridView servicesGridView = findViewById(R.id.services_gridview);
        servicesGridView.setAdapter(new ServicesAdapter(this));
        TextView emptyView = findViewById(R.id.services_empty_text_view);
        new KubernetesClient().getServices(servicesGridView, namespace, emptyView);
    }

    public void setItemListener() {
        final GridView servicesGridView = findViewById(R.id.services_gridview);
        servicesGridView.setOnItemClickListener((parent, view, position, id) -> {
            Service clickedService = (Service)servicesGridView.getAdapter().getItem(position);
            Intent intent = new Intent(getBaseContext(), ServiceActivity.class);
            intent.putExtra("service", clickedService);
            startActivity(intent);
        });
    }
}
