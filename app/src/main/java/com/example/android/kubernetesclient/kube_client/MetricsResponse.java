package com.example.android.kubernetesclient.kube_client;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MetricsResponse {

    @SerializedName("metrics")
    private List<MetricResponse> metrics;

    public class MetricResponse {
        @SerializedName("memory_value")
        private Float memoryValue;

        @SerializedName("cpu_value")
        private Float cpuValue;

        @SerializedName("timestamp")
        private Integer timestamp;

        @SerializedName("resource_name")
        private String resourceName;

        public Float getMemoryValue() {
            return memoryValue;
        }

        public Float getCpuValue() {
            return cpuValue;
        }

        public Integer getTimestamp() {
            return timestamp;
        }

        public String getResourceName() {
            return resourceName;
        }
    }

    public List<MetricResponse> getMetrics() {
        return metrics;
    }

}
