package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query;

/**
 * Exception for when a query syntax error occurs.
 */
public class QuerySyntaxErrorException extends Exception {

  public QuerySyntaxErrorException(String criteria) {
    super("Error in the syntax of the query : " + criteria + ", check the data entered");
  }
}