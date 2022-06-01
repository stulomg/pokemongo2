package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions;

/**
 * Exception for when try a querying with an owner that already registered.
 */
public class OwnerAlreadyExists extends Exception {

  public OwnerAlreadyExists() {
    super("Owner already registered");
  }
}