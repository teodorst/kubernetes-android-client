package com.example.android.kubernetesclient.kube_client;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KubernetesResourceResponse {
    @SerializedName("items")
    private List<KubeResource> items;

    public List<KubeResource> getItems() {
        return items;
    }

    public void setItems(List<KubeResource> items) {
        this.items = items;
    }

    class KubeResource {

        @SerializedName("metadata")
        KubeMetadataResource metadata;

        @SerializedName("status")
        KubeStatusResource status;

        public KubeMetadataResource getMetadata() {
            return metadata;
        }

        public void setMetadata(KubeMetadataResource metadata) {
            this.metadata = metadata;
        }

        public KubeStatusResource getStatus() {
            return status;
        }

        public void setStatus(KubeStatusResource status) {
            this.status = status;
        }
    }

    class KubeMetadataResource {

        @SerializedName("name")
        String name;
        @SerializedName("namespace")
        String namespace;
        @SerializedName("uid")
        String uid;
        @SerializedName("creationTimestamp")
        String creationTimestamp;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCreationTimestamp() {
            return creationTimestamp;
        }

        public void setCreationTimestamp(String creationTimestamp) {
            this.creationTimestamp = creationTimestamp;
        }
    }

    class KubeStatusResource {
        @SerializedName("phase")
        String phase;

        public String getStatus() {
            return phase;
        }

        public void setStatus(String status) {
            this.phase = status;
        }
    }
}
