package com.example.android.kubernetesclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.android.kubernetesclient.kube_client.KubernetesClient;

import java.util.ArrayList;
import java.util.List;

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

        new KubernetesClient().getNamespaces(namespaceSpinner, namespaces);
    }

    public void setSpinnerListener() {
        Spinner namespaceSpinner = findViewById(R.id.namespaces_spinner);
        namespaceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNamespace = namespaces.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    public void setMenuClickListeners() {
        Button podsButton = findViewById(R.id.podsButton);
        podsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PodsActivity.class);
                intent.putExtra("namespace", selectedNamespace);
                startActivity(intent);
            }
        });

        Button servicesButton = findViewById(R.id.servicesButton);
        servicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ServicesActivity.class);
                intent.putExtra("namespace", selectedNamespace);
                startActivity(intent);

            }
        });

        Button nodesButton = findViewById(R.id.nodesButton);
        nodesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NodesActivity.class);
                intent.putExtra("namespace", selectedNamespace);
                startActivity(intent);
            }
        });

    }
}
