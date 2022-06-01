package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/**
 * Model for the query.
 */
public class QueryModel {

  private String criteria;
  private String query;

  public QueryModel() {
  }

  public QueryModel(String criteria, String query) {
    this.criteria = criteria;
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
