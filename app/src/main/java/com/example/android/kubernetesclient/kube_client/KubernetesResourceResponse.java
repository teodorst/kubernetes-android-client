package com.example.android.kubernetesclient.kube_client;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KubernetesResourceResponse {
    @SerializedName("items")
    private List<KubeResource> items;

    public List<KubeResource> getItems() {
        return items;
    }

    class KubeResource {

        @SerializedName("metadata")
        KubeMetadataResource metadata;

        @SerializedName("status")
        KubeStatusResource status;

        @SerializedName("spec")
        KubeSpecResource spec;

        public KubeMetadataResource getMetadata() {
            return metadata;
        }

        public KubeStatusResource getStatus() {
            return status;
        }

        public void setStatus(KubeStatusResource status) {
            this.status = status;
        }

        public KubeSpecResource getSpec() {
            return spec;
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

    }

    class KubeStatusResource {
        @SerializedName("phase")
        String phase;

        public String getPhase() {
            return phase;
        }
    }

    class KubeSpecResource {

        @SerializedName("ports")
        List<KubePortResource> ports;

        @SerializedName("clusterIP")
        String clusterIP;

        @SerializedName("type")
        String type;

        class KubePortResource {
            @SerializedName("protocol")
            String protocol;
            @SerializedName("port")
            Integer port;
            @SerializedName("targetPort")
            Integer targetPort;

            public String getProtocol() {
                return protocol;
            }

            public Integer getPort() {
                return port;
            }

            public Integer getTargetPort() {
                return targetPort;
            }
        }

        public List<KubePortResource> getPorts() {
            return ports;
        }

        public String getClusterIP() {
            return clusterIP;
        }

        public String getType() {
            return type;
        }
    }

}
