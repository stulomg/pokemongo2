package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class SpecificQueryModel {
    private String criteria;
    private String query;

    public SpecificQueryModel() {
    }

    public SpecificQueryModel(String criterio, String query) {
        this.criteria = criterio;
        this.query = query;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
