package com.example.android.kubernetesclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.kubernetesclient.adapters.ServicesAdapter;
import com.example.android.kubernetesclient.kube_client.KubernetesClient;
import com.example.android.kubernetesclient.kube_client.KubernetesResourceResponse;
import com.example.android.kubernetesclient.models.Service;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        new KubernetesClient().getServices( new Callback<KubernetesResourceResponse>() {
            @Override
            public void onResponse(Call<KubernetesResourceResponse> call,
                                   Response<KubernetesResourceResponse> response) {

                List<Service> services = new ArrayList<>();
                if (response.body() != null) {
                    for (KubernetesResourceResponse.KubeResource item : response.body().getItems()) {
                        Service service = new Service(item.getMetadata().getName(),
                                item.getMetadata().getNamespace(), item.getMetadata().getUid(),
                                item.getMetadata().getCreationTimestamp(),
                                item.getSpec().getClusterIP(),
                                item.getSpec().getType(),
                                item.getSpec().getPorts().get(0).getProtocol(),
                                item.getSpec().getPorts().get(0).getPort(),
                                item.getSpec().getPorts().get(0).getTargetPort());
                        services.add(service);
                    }
                }

                if (!namespace.equals("All")) {
                    services = Lists.newArrayList(Collections2.filter(
                            services, s -> s.getNamespace().equals(namespace)));
                }

                if (!services.isEmpty()) {
                    ServicesAdapter adapter = (ServicesAdapter) servicesRecyclerGridView.getAdapter();
                    adapter.setServices(services);
                    adapter.notifyDataSetChanged();
                    servicesRecyclerGridView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                } else {
                    emptyView.setText("Couldn't load services. Try again later!");
                    servicesRecyclerGridView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<KubernetesResourceResponse> call, Throwable t) {
                Log.e("Error getting pods:", t.toString());
                emptyView.setText("Couldn't load services. Try again later!");
                servicesRecyclerGridView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
        });
    }

}
