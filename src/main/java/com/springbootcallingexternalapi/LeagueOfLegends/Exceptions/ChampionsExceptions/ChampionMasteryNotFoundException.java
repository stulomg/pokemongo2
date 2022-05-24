package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions;

public class ChampionMasteryNotFoundException extends Exception {
    public ChampionMasteryNotFoundException(String championName) {
        super("This account has no mastery with: " + championName);
    }
}