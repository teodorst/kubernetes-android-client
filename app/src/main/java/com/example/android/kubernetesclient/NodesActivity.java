package com.example.android.kubernetesclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.kubernetesclient.adapters.NodesAdapter;
import com.example.android.kubernetesclient.kube_client.KubernetesClient;
import com.example.android.kubernetesclient.kube_client.KubernetesResourceResponse;
import com.example.android.kubernetesclient.models.Node;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NodesActivity extends AppCompatActivity {

    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodes);

        gridLayoutManager = new GridLayoutManager(this, 3);
        loadNodes();
    }

    public void loadNodes() {
        RecyclerView nodesRecyclerGridView = findViewById(R.id.nodes_recyclerview);
        nodesRecyclerGridView.setAdapter(new NodesAdapter(new ArrayList<>()));
        nodesRecyclerGridView.setLayoutManager(gridLayoutManager);
        new KubernetesClient().getNodes(
            new Callback<KubernetesResourceResponse>() {
                @Override
                public void onResponse(@NonNull Call<KubernetesResourceResponse> call,
                                       @NonNull Response<KubernetesResourceResponse> response) {

                    List<Node> nodes = new ArrayList<>();
                    if (response.body() != null) {
                        for (KubernetesResourceResponse.KubeResource item : response.body().getItems()) {
                            Integer cpuNo = Integer.parseInt(item.getStatus().getCapacity().getCpu());
                            String memoryString = item.getStatus().getCapacity().getMemory();
                            Integer memory = Integer.parseInt(memoryString.substring(0, memoryString.length()-2));
                            Node node = new Node(item.getMetadata().getName(),
                                    item.getMetadata().getNamespace(), item.getMetadata().getUid(),
                                    item.getMetadata().getCreationTimestamp(), cpuNo, memory,
                                    item.getStatus().getAddresses().get(0).getAddress(),
                                    item.getStatus().getAddresses().get(1).getAddress(),
                                    item.getStatus().getNodeInfo().getOsImage(),
                                    item.getStatus().getNodeInfo().getKernelVersion(),
                                    item.getStatus().getNodeInfo().getArchitecture());
                            nodes.add(node);
                        }
                    }

                    if (!nodes.isEmpty()) {
                        NodesAdapter adapter = (NodesAdapter) nodesRecyclerGridView.getAdapter();
                        adapter.setNodes(nodes);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<KubernetesResourceResponse> call, @NonNull Throwable t) {
                    Log.e("Error getting pods:", t.toString());
                }
            });
    }
}
