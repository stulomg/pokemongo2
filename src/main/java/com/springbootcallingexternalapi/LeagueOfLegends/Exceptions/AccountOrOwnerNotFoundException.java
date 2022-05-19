package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions;

public class AccountOrOwnerNotFoundException extends Exception {
    public AccountOrOwnerNotFoundException(String owner, String account) {
        super("The account: " + account + ", linked to the user: " + owner + " was not found, please rectify.");
    }
}