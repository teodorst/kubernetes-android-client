package com.example.android.kubernetesclient.kube_client;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KubernetesResourceResponse {
    @SerializedName("items")
    private List<KubeResource> items;

    public List<KubeResource> getItems() {
        return items;
    }

    public class KubeResource {

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

    public class KubeMetadataResource {

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

    public class KubeStatusResource {
        @SerializedName("phase")
        String phase;

        @SerializedName("capacity")
        KubeCapacityResource capacity;

        @SerializedName("addresses")
        List<KubeAddressResource> addresses;

        @SerializedName("nodeInfo")
        KubeNodeInfoResource nodeInfo;

        public class KubeCapacityResource {
            @SerializedName("cpu")
            String cpu;
            @SerializedName("memory")
            String memory;

            public String getCpu() {
                return cpu;
            }

            public String getMemory() {
                return memory;
            }
        }

        public class KubeAddressResource {
            @SerializedName("type")
            String type;
            @SerializedName("addresses")
            String address;

            public String getType() {
                return type;
            }

            public String getAddress() {
                return address;
            }
        }

        public class KubeNodeInfoResource {
            @SerializedName("kernelVersion")
            String kernelVersion;

            @SerializedName("osImage")
            String osImage;

            @SerializedName("architecture")
            String architecture;

            public String getKernelVersion() {
                return kernelVersion;
            }

            public String getOsImage() {
                return osImage;
            }

            public String getArchitecture() {
                return architecture;
            }
        }

        public String getPhase() {
            return phase;
        }

        public KubeCapacityResource getCapacity() {
            return capacity;
        }

        public List<KubeAddressResource> getAddresses() {
            return addresses;
        }

        public KubeNodeInfoResource getNodeInfo() {
            return nodeInfo;
        }
    }

    public class KubeSpecResource {

        @SerializedName("ports")
        List<KubePortResource> ports;

        @SerializedName("clusterIP")
        String clusterIP;

        @SerializedName("type")
        String type;

        public class KubePortResource {
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
