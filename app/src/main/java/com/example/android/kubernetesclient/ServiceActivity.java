package com.example.android.kubernetesclient;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.kubernetesclient.models.Pod;
import com.example.android.kubernetesclient.models.Service;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Service service = loadService();
        displayServiceData(service);
    }


    public Service loadService() {
        return (Service)getIntent().getSerializableExtra("service");
    }

    @SuppressLint("DefaultLocale")
    public void displayServiceData(Service service) {
        TextView serviceNameView = findViewById(R.id.service_name);
        serviceNameView.setText(service.getName());

        TextView serviceUIDView = findViewById(R.id.service_uid);
        serviceUIDView.setText(service.getUid());

        TextView serviceNamespaceView = findViewById(R.id.service_namespace);
        serviceNamespaceView.setText(service.getNamespace());

        TextView serviceCreatedAtView = findViewById(R.id.service_createdat);
        String date = service.getCreatedTimestamp().toString();

        serviceCreatedAtView.setText(date.substring(0, date.length()-2));

        TextView serviceIpTypeView = findViewById(R.id.service_ip_type);
        serviceIpTypeView.setText(service.getIPType());

        TextView serviceIpView = findViewById(R.id.service_ip);
        serviceIpView.setText(service.getIP());

        TextView protocolView = findViewById(R.id.service_protocol);
        protocolView.setText(service.getProtocol());

        TextView portView = findViewById(R.id.service_port);
        portView.setText(String.format("%d", service.getPort()));

        TextView targetPortView = findViewById(R.id.service_target_port);
        targetPortView.setText(String.format("%d", service.getTargetPort()));
    }
}
