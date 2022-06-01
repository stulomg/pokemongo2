package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions;

/**
 * Exception for when try a querying with an not allowed owner.
 */
public class OwnerNotAllowedException extends Exception {

  public OwnerNotAllowedException(String owner) {
    super("The owner: " + owner + " is not allowed for this api");
  }
}