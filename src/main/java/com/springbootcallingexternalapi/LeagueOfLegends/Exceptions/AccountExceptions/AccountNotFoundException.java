package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String account) {
        super(" The account " + account + " was not found, please rectify");
    }
}