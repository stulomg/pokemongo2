package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions;

public class LeagueDataNotFoundException extends Exception {

  public LeagueDataNotFoundException(String account) {
    super("The SoloQ data for " + account + " does not exist");
  }
}
