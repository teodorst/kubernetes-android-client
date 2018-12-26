package com.example.android.kubernetesclient.models;

import android.util.Log;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pod implements Serializable {
    private String name;
    private String namespace;
    private String uid;
    private Timestamp createdTimestamp;
    private String status;

    public Pod (String name, String namespace, String uid, Timestamp createdTimestamp, String status) {
        this.name = name;
        this.namespace = namespace;
        this.uid = uid;
        this.createdTimestamp = createdTimestamp;
        this.status = status;
    }

    public Pod(String name, String namespace, String uid, String createdTimestampString, String status) {
        this.name = name;
        this.namespace = namespace;
        this.uid = uid;
        this.status = status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isRunning() {
        return status.equals("Running");
    }
}

