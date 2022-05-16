package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class QuerySpecificModel {
    private String criterio;
    private String query;

    public QuerySpecificModel() {
    }

    public QuerySpecificModel(String criterio, String query) {
        this.criterio = criterio;
        this.query = query;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
