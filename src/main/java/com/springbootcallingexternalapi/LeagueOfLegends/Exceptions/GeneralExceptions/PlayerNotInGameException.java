package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions;

/** Exception for when an invalid character is entered for an owner.*/
public class PlayerNotInGameException extends Exception {

  public PlayerNotInGameException(String account) {
    super("The player is not on a live match");
  }
}