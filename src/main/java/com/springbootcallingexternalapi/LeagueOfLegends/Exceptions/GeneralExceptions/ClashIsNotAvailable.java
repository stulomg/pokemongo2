package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions;

/**
 * Exception for a Clash not available.
 */
public class ClashIsNotAvailable extends Exception {

  public ClashIsNotAvailable() {
    super("Clash is not available");
  }
}