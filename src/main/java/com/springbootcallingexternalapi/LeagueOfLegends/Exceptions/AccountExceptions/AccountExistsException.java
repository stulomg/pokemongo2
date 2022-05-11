package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;

public class AccountExistsException extends Exception{
    public AccountExistsException (){
        super ("Account already registered");
    }
}
