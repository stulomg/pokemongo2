package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions;

/**Exception for not relationship. */
public class NotRelationshipException extends Exception {

  public NotRelationshipException(String account1, String account2) {
    super("The player " + account1 + " and the player " + account2 + " have no relationship");
  }
}
