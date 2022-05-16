package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class QueryModel {
    private String criterio;
    private String select;
    private String from;
    private String where;

    public QueryModel() {
    }

    public QueryModel(String criterio, String select, String from, String where) {
        this.criterio = criterio;
        this.select = select;
        this.from = from;
        this.where = where;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }
}
