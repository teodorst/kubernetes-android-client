package com.example.android.kubernetesclient.models;

import android.util.Log;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Node implements Serializable {

    private String name;
    private String uid;
    private String namespace;
    private Timestamp createdTimestamp;
    private Integer cpuNo;
    private Double memory;
    private String internalIp;
    private String hostname;
    private String OS;
    private String kernelVersion;
    private String architecture;

    public Node(String name, String namespace, String uid, String createdTimestampString,
                Integer cpuNo, Integer memory, String internalIp, String hostname,
                String OS, String kernelVersion, String architecture) {
        this.name = name;
        this.namespace = namespace;
        this.uid = uid;
        this.cpuNo = cpuNo;
        this.internalIp = internalIp;
        this.hostname = hostname;
        this.OS = OS;
        this.kernelVersion = kernelVersion;
        this.architecture = architecture;

        this.memory = memory * 1.024 / 1000000; // From KiB to GB

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            Date parsedDate = dateFormat.parse(createdTimestampString);
            this.createdTimestamp = new Timestamp(parsedDate.getTime());
        } catch(Exception e) { //this generic but you can control another types of exception
            // look the origin of excption
            Log.e("time nu merge", e.toString());
        }
    }

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

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public Integer getCpuNo() {
        return cpuNo;
    }

    public Double getMemory() {
        return memory;
    }

    public String getInternalIp() {
        return internalIp;
    }

    public String getHostname() {
        return hostname;
    }

    public String getOS() {
        return OS;
    }

    public String getKernelVersion() {
        return kernelVersion;
    }

    public String getArchitecture() {
        return architecture;
    }
}
