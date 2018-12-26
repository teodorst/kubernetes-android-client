package com.example.android.kubernetesclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.kubernetesclient.models.Pod;

public class PodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod);
        Pod pod = loadPod();
        displayPodData(pod);
    }

    public Pod loadPod() {
        return (Pod)getIntent().getSerializableExtra("pod");
    }

    public void displayPodData(Pod pod) {
        TextView podNameView = findViewById(R.id.pod_name);
        podNameView.setText(pod.getName());

        TextView podUIDView = findViewById(R.id.pod_uid);
        podUIDView.setText(pod.getUid());

        TextView podNamespaceView = findViewById(R.id.pod_namespace);
        podNamespaceView.setText(pod.getNamespace());

        TextView podCreatedAtView = findViewById(R.id.pod_createdat);
        String date = pod.getCreatedTimestamp().toString();

        podCreatedAtView.setText(date.substring(0, date.length()-2));

        ImageView podStatusView = findViewById(R.id.pod_status);
        int podImageResourceId;
        if (pod.isRunning()) {
            podImageResourceId = R.drawable.success;
        } else {
            podImageResourceId = R.drawable.error;
        }
        podStatusView.setImageResource(podImageResourceId);
    }
}
