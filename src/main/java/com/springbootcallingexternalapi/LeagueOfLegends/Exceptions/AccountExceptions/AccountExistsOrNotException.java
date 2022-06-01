package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions;

public class AccountExistsOrNotException extends Exception {

  public AccountExistsOrNotException() {
    super("Account already registered or Owner not exists");
  }
}