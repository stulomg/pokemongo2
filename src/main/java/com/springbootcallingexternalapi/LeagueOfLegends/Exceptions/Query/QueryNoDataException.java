package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query;

/** Exception for when there are no stored queries.*/
public class QueryNoDataException extends Exception {

  public QueryNoDataException() {
    super("There are currently no queries stored");
  }
}