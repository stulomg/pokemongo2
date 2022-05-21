package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.ChampionsExceptions;

public class ChampionNotFoundException extends Exception {
    public ChampionNotFoundException(String championName) {
        super("The champion " + championName + " was not found");
    }
}