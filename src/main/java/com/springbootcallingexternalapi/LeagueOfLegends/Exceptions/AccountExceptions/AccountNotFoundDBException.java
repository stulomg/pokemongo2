package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions;

public class AccountNotFoundDBException extends Exception {

  public AccountNotFoundDBException(String account) {
    super("The account " + account + " was not registered");
  }
}
