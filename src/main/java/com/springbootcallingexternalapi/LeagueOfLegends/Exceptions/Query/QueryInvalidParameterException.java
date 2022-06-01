package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query;

public class QueryInvalidParameterException extends Exception {

  public QueryInvalidParameterException(String criteria) {
    super("Invalid parameter in the query : " + criteria + ", check the data entered");
  }
}
