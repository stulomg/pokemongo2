package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions;

/** Exception for when a account has not Soloq data. */
public class LeagueDataNotFoundException extends Exception {

  public LeagueDataNotFoundException(String account) {
    super("The SoloQ data for " + account + " does not exist");
  }
}