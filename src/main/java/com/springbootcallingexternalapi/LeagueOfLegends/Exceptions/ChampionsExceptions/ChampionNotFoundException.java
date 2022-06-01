package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions;

/**
 * Exceptions for when a champion name was not found.
 */
public class ChampionNotFoundException extends Exception {

  public ChampionNotFoundException(String championName) {
    super("The champion " + championName + " was not found");
  }
}