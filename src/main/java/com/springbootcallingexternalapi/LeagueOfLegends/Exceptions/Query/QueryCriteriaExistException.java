package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query;

/** Exception for when a criteria are already registered.*/
public class QueryCriteriaExistException extends Exception {

  public QueryCriteriaExistException(String criteria) {
    super("The criteria: " + criteria + ", is already registered, please use another name");
  }
}