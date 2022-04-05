package com.springbootcallingexternalapi.Models;

public class OwnerModel {

    private String id;
    private String name;

    public OwnerModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public OwnerModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

