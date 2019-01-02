package com.example.android.kubernetesclient.models;


public class Metric {
    private Float memoryValue;

    private Float cpuValue;

    private Integer timestamp;

    private String resourceName;

    public Metric(Float memoryValue, Float cpuValue, Integer timestamp, String resourceName) {
        this.memoryValue = memoryValue;
        this.cpuValue = cpuValue;
        this.timestamp = timestamp;
        this.resourceName = resourceName;
    }

    public Float getMemoryValue() {
        return memoryValue;
    }

    public void setMemoryValue(Float memoryValue) {
        this.memoryValue = memoryValue;
    }

    public Float getCpuValue() {
        return cpuValue;
    }

    public void setCpuValue(Float cpuValue) {
        this.cpuValue = cpuValue;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "memoryValue=" + memoryValue +
                ", cpuValue=" + cpuValue +
                ", timestamp=" + timestamp +
                ", resourceName='" + resourceName + '\'' +
                '}';
    }
}
