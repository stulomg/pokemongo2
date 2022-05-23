package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions;

public class AccountNotFoundBDException extends Exception{
    public AccountNotFoundBDException(String account) {
        super("The account "+account+" was not registered");
    }
}
