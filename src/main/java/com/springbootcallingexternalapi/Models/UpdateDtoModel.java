package com.springbootcallingexternalapi.Models;

import java.awt.*;

public class UpdateDtoModel {

    private int id;

    private String author;

    private  boolean publish;

    private List[] publish_locatios;

    private String created_at;

    private String updated_at;

    public UpdateDtoModel(int id, String author, boolean publish, List[] publish_locatios, String created_at, String updated_at) {
        this.id = id;
        this.author = author;
        this.publish = publish;
        this.publish_locatios = publish_locatios;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public UpdateDtoModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public List[] getPublish_locatios() {
        return publish_locatios;
    }

    public void setPublish_locatios(List[] publish_locatios) {
        this.publish_locatios = publish_locatios;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
