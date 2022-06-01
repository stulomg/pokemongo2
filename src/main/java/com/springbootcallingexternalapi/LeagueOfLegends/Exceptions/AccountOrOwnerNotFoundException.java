package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions;

/**
 * Exception for when an account linked to a user is not found.
 */
public class AccountOrOwnerNotFoundException extends Exception {

  public AccountOrOwnerNotFoundException(String owner, String account) {
    super("The account: " + account + ", linked to the user: " + owner
        + " was not found, please rectify.");
  }
}