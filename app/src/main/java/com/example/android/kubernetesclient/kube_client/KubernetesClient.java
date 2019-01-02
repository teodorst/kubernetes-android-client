package com.example.android.kubernetesclient.kube_client;

import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.kubernetesclient.adapters.NodesAdapter;
import com.example.android.kubernetesclient.adapters.PodsAdapter;
import com.example.android.kubernetesclient.adapters.ServicesAdapter;
import com.example.android.kubernetesclient.models.Namespace;
import com.example.android.kubernetesclient.models.Node;
import com.example.android.kubernetesclient.models.Pod;
import com.example.android.kubernetesclient.models.Service;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KubernetesClient {
    KubernetesAPIInterface service;

    public KubernetesClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.89.110.74:8001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(KubernetesAPIInterface.class);
    }

    public void getPods(final GridView podsGridView, String namespace, TextView emptyView) {
        Call<KubernetesResourceResponse> call = this.service.getPods();
        Log.d("Item", "Pornesc call");
        call.enqueue(new Callback<KubernetesResourceResponse>() {
            @Override
            public void onResponse(Call<KubernetesResourceResponse> call,
                                   Response<KubernetesResourceResponse> response) {

                List<Pod> pods = new ArrayList<>();
                if (response.body() != null) {
                    for (KubernetesResourceResponse.KubeResource item : response.body().getItems()) {
                        Pod pod = new Pod(item.getMetadata().getName(), item.getMetadata().getNamespace(), item.getMetadata().getUid(),
                                item.getMetadata().getCreationTimestamp(), item.getStatus().getPhase());
                        pods.add(pod);
                    }
                }

                if (!namespace.equals("All")) {
                    pods = Lists.newArrayList(Collections2.filter(
                            pods, p -> p.getNamespace().equals(namespace)));
                }

                if (!pods.isEmpty()) {
                    PodsAdapter adapter = (PodsAdapter) podsGridView.getAdapter();
                    adapter.setPods(pods);
                    adapter.notifyDataSetChanged();
                    emptyView.setVisibility(View.GONE);
                } else {
                    podsGridView.setEmptyView(emptyView);
                }
            }

            @Override
            public void onFailure(Call<KubernetesResourceResponse> call, Throwable t) {
                Log.e("Error getting pods:", t.toString());
            }
        });
    }

    public void getServices(final GridView servicesGridView, final String namespace, TextView emptyView) {
        Call<KubernetesResourceResponse> call = this.service.getServices();
        Log.d("Item", "Pornesc call");
        call.enqueue(new Callback<KubernetesResourceResponse>() {
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
                    ServicesAdapter adapter = (ServicesAdapter) servicesGridView.getAdapter();
                    adapter.setServices(services);
                    adapter.notifyDataSetChanged();
                } else {
                    servicesGridView.setEmptyView(emptyView);
                }
            }

            @Override
            public void onFailure(Call<KubernetesResourceResponse> call, Throwable t) {
                Log.e("Error getting pods:", t.toString());
            }
        });
    }

    public void getNodes(final GridView nodesGridView) {
        Call<KubernetesResourceResponse> call = this.service.getNodes();
        Log.d("Item", "Pornesc call");
        call.enqueue(new Callback<KubernetesResourceResponse>() {
            @Override
            public void onResponse(Call<KubernetesResourceResponse> call,
                                   Response<KubernetesResourceResponse> response) {

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
                    NodesAdapter adapter = (NodesAdapter) nodesGridView.getAdapter();
                    adapter.setNodes(nodes);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<KubernetesResourceResponse> call, Throwable t) {
                Log.e("Error getting pods:", t.toString());
            }
        });
    }

    public void getNamespaces(final Spinner spinner, final List<String> namespaces) {
        Call<KubernetesResourceResponse> call = this.service.getNamespaces();
        Log.d("Item", "Pornesc call");
        call.enqueue(new Callback<KubernetesResourceResponse>() {
            @Override
            public void onResponse(Call<KubernetesResourceResponse> call,
                                   Response<KubernetesResourceResponse> response) {

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

                    ((BaseAdapter)spinner.getAdapter()).notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<KubernetesResourceResponse> call, Throwable t) {
                Log.e("Error getting pods:", t.toString());
            }
        });
    }
}
