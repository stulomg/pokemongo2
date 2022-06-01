package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions;

/** Exceptions for when an account is not in the database.*/
public class AccountNotFoundDBException extends Exception {

  public AccountNotFoundDBException(String account) {
    super("The account " + account + " was not registered");
  }
}