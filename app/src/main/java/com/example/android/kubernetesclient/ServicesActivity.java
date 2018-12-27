package com.example.android.kubernetesclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.kubernetesclient.adapters.PodsAdapter;
import com.example.android.kubernetesclient.adapters.ServicesAdapter;
import com.example.android.kubernetesclient.kube_client.KubernetesClient;
import com.example.android.kubernetesclient.models.Pod;
import com.example.android.kubernetesclient.models.Service;

public class ServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        loadServices();
        setItemListener();
    }

    public void loadServices() {
        GridView servicesGridView = findViewById(R.id.services_gridview);
        servicesGridView.setAdapter(new ServicesAdapter(this));
        new KubernetesClient().getServices(servicesGridView);
    }

    public void setItemListener() {
        final GridView servicesGridView = findViewById(R.id.services_gridview);
        servicesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service clickedService = (Service)servicesGridView.getAdapter().getItem(position);
                Intent intent = new Intent(getBaseContext(), ServiceActivity.class);
                intent.putExtra("service", clickedService);
                startActivity(intent);
            }
        });
    }
}
