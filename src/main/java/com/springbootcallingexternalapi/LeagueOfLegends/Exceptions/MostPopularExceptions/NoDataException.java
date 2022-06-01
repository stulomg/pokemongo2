package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions;

/** Exception for when try a query with insufficient data.*/
public class NoDataException extends Exception {

  public NoDataException() {
    super("There is not enough data to perform the query");
  }
}