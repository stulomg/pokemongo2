package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document (value = "tweets")
public class TweetModel {

    private String id;
    private String text;

    public TweetModel(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public TweetModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
