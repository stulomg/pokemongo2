package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class QueryModel {
    private String criteria;
    private String select;
    private String from;
    private String where;

    public QueryModel() {
    }

    public QueryModel(String criteria, String select, String from, String where) {
        this.criteria = criteria;
        this.select = select;
        this.from = from;
        this.where = where;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
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
