package com.example.android.kubernetesclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.kubernetesclient.models.Node;

public class NodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);

        Node node = loadNode();
        displayNodeData(node);
    }

    public Node loadNode() {
        return (Node)getIntent().getSerializableExtra("node");
    }

    @SuppressLint("DefaultLocale")
    public void displayNodeData(Node node) {
        TextView podNameView = findViewById(R.id.node_name);
        podNameView.setText(node.getName());

        TextView nodeUIDView = findViewById(R.id.node_uid);
        nodeUIDView.setText(node.getUid());

        TextView nodeOsView = findViewById(R.id.node_os);
        nodeOsView.setText(node.getOS());

        TextView nodeCpusView = findViewById(R.id.node_cpus);
        nodeCpusView.setText(String.format("%d", node.getCpuNo()));

        TextView nodeMemoryView = findViewById(R.id.node_memory);
        nodeMemoryView.setText(String.format("%.2f GB", node.getMemory()));

    }
}
