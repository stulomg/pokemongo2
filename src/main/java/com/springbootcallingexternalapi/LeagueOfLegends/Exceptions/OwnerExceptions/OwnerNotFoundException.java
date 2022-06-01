package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions;

/** Exception for when an owner was not found.*/
public class OwnerNotFoundException extends Exception {

  public OwnerNotFoundException(String owner) {
    super("The owner " + owner + " was not found, please rectify");
  }

  public OwnerNotFoundException(String owner, String owner2) {
    super("The owner " + owner + " and the owner " + owner2 + " were not found, please rectify");
  }
}