package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions;

/**
 * Exceptions for when an account was not found.
 */
public class AccountNotFoundException extends Exception {

  public AccountNotFoundException(String account) {
    super("The account " + account + " was not found, please rectify");
  }
}