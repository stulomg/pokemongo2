package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions;

/** Exception for when a database connection error occurs. */
public class DbNotAvaliableException extends Exception {

  public DbNotAvaliableException() {
    super("Error establishing connection to database");
  }
}