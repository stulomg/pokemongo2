package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query;

/**
 * Exception for when the criteria has no query.
 */
public class QueryFilterNoDataException extends Exception {

  public QueryFilterNoDataException(String criteria) {
    super("There is no query with the criteria: " + criteria);
  }
}