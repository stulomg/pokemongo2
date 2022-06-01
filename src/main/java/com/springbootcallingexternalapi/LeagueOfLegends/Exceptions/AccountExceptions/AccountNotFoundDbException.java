package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions;

/** Exceptions for when an account is not in the database.*/
public class AccountNotFoundDbException extends Exception {

  public AccountNotFoundDbException(String account) {
    super("The account " + account + " was not registered");
  }
}