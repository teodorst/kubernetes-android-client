package com.example.android.kubernetesclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.android.kubernetesclient.kube_client.KubernetesClient;
import com.example.android.kubernetesclient.kube_client.KubernetesResourceResponse;
import com.example.android.kubernetesclient.models.Namespace;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String selectedNamespace;
    List<String> namespaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        namespaces = new ArrayList<>();
        namespaces.add("All");
        selectedNamespace = "All";

        setContentView(R.layout.activity_main);
        fetchNamespaces();
        setMenuClickListeners();
        setSpinnerListener();
    }

    public void fetchNamespaces() {
        Spinner namespaceSpinner = findViewById(R.id.namespaces_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, namespaces);
        namespaceSpinner.setAdapter(adapter);

        new KubernetesClient().getNamespaces(new Callback<KubernetesResourceResponse>() {
            @Override
            public void onResponse(@NonNull Call<KubernetesResourceResponse> call,
                                   @NonNull Response<KubernetesResourceResponse> response) {

                List<Namespace> namespacesFromNetwork = new ArrayList<>();
                if (response.body() != null) {
                    for (KubernetesResourceResponse.KubeResource item : response.body().getItems()) {
                        Namespace namespace = new Namespace(item.getMetadata().getName());
                        namespacesFromNetwork.add(namespace);
                    }
                }

                if (!namespacesFromNetwork.isEmpty()) {
                    for (Namespace namespace : namespacesFromNetwork) {
                        namespaces.add(namespace.getName());
                    }

                    ((BaseAdapter)namespaceSpinner.getAdapter()).notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<KubernetesResourceResponse> call, @NonNull Throwable t) {
                Log.e("Error getting pods:", t.toString());
            }
        });
    }

    public void setSpinnerListener() {
        Spinner namespaceSpinner = findViewById(R.id.namespaces_spinner);
        namespaceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNamespace = namespaces.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

        });
    }

    public void setMenuClickListeners() {
        Button podsButton = findViewById(R.id.podsButton);
        podsButton.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), PodsActivity.class);
            intent.putExtra("namespace", selectedNamespace);
            startActivity(intent);
        });

        Button servicesButton = findViewById(R.id.servicesButton);
        servicesButton.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), ServicesActivity.class);
            intent.putExtra("namespace", selectedNamespace);
            startActivity(intent);

        });

        Button nodesButton = findViewById(R.id.nodesButton);
        nodesButton.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), NodesActivity.class);
            intent.putExtra("namespace", selectedNamespace);
            startActivity(intent);
        });

    }
}
