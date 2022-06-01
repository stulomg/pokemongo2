package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions;

public class ChampionNotFoundException extends Exception {

  public ChampionNotFoundException(String championName) {
    super("The champion " + championName + " was not found");
  }
}