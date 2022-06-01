package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query;

/**
 * Exception for when you try a query with an invalid parameter.
 */
public class QueryInvalidParameterException extends Exception {

  public QueryInvalidParameterException(String criteria) {
    super("Invalid parameter in the query : " + criteria + ", check the data entered");
  }
}