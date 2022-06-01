package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions;

/** Exception for when an invalid character is entered for a name.*/
public class CharacterNotAllowedException extends Exception {

  public CharacterNotAllowedException(String name) {
    super(name + " has characters not allowed");
  }
}