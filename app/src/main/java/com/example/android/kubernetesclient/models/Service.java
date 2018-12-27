package com.example.android.kubernetesclient.models;

import android.util.Log;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Service implements Serializable {

    private String name;
    private String namespace;
    private String uid;
    private Timestamp createdTimestamp;
    private String IP;
    private String IPType;
    private String protocol;
    private Integer port;
    private Integer targetPort;

    public Service(String name, String namespace, String uid, String createdTimestampString,
                   String IP, String IPType, String protocol, Integer port, Integer targetPort) {
        this.name = name;
        this.namespace = namespace;
        this.uid = uid;

        this.IP = IP;
        this.IPType = IPType;
        this.protocol = protocol;
        this.port = port;
        this.targetPort = targetPort;

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

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getIPType() {
        return IPType;
    }

    public void setIPType(String IPType) {
        this.IPType = IPType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(Integer targetPort) {
        this.targetPort = targetPort;
    }
}
