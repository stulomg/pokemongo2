package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions;

/** Exceptions for entered account data.*/
public class AccountExistsOrNotException extends Exception {

  public AccountExistsOrNotException() {
    super("Account already registered or Owner not exists");
  }
}