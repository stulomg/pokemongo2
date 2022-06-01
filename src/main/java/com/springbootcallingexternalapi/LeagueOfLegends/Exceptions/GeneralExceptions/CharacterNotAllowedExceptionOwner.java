package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions;

/**
 * Exception for when an invalid character is entered for an owner.
 */
public class CharacterNotAllowedExceptionOwner extends Exception {

  public CharacterNotAllowedExceptionOwner(String owner, String owner2) {
    super(owner + " or " + owner2 + " has characters not allowed");
  }
}