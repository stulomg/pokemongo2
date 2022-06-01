package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions;

/** Exception for when a database connection error occurs. */
public class DBNotAvaliableException extends Exception {

  public DBNotAvaliableException() {
    super("Error establishing connection to database");
  }
}