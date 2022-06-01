package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions;

public class PlayerNotInGameException extends Exception {

  public PlayerNotInGameException(String account) {
    super("The player is not on a live match");
  }
}
