package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions;

public class OwnerAlreadyExists extends Exception {

  public OwnerAlreadyExists() {
    super("Owner already registered");
  }
}
