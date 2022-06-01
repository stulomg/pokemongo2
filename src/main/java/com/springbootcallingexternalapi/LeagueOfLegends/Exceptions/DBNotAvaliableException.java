package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions;

public class DBNotAvaliableException extends Exception {

  public DBNotAvaliableException() {
    super("Error establishing connection to database");
  }
}
