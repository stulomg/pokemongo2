package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class ContentDtoModel {

    private String locale;

    private String content;

    public ContentDtoModel(String locale, String content) {
        this.locale = locale;
        this.content = content;
    }

    public ContentDtoModel() {
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
