package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions;

/**
 * Exceptions for when a champion has not mastery.
 */
public class ChampionMasteryNotFoundException extends Exception {

  public ChampionMasteryNotFoundException(String championName) {
    super("This account has no mastery with: " + championName);
  }
}