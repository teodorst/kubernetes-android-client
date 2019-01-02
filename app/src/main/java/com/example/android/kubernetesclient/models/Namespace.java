package com.example.android.kubernetesclient.models;

public class Namespace {

    private String name;

    public Namespace(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
