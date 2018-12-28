package com.example.android.kubernetesclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMenuClickListeners();
    }

    public void setMenuClickListeners() {
        Button podsButton = findViewById(R.id.podsButton);
        podsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PodsActivity.class);
                startActivity(intent);
            }
        });

        Button servicesButton = findViewById(R.id.servicesButton);
        servicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ServicesActivity.class);
                startActivity(intent);
            }
        });

        Button nodesButton = findViewById(R.id.nodesButton);
        nodesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NodesActivity.class);
                startActivity(intent);
            }
        });

    }
}
