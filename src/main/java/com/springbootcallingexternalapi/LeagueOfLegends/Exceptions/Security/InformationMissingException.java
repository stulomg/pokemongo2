package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Security;

public class InformationMissingException extends Exception{
    public InformationMissingException (){
        super ("Missing mandatory information : name,username,email,password.");
    }
}
