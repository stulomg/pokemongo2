package com.springbootcallingexternalapi.Exceptions.ChampionsExceptions;

public class ChampionMasteryNotFoundException extends Exception {
    public ChampionMasteryNotFoundException(String championName) {
        super("Esta cuenta no tiene maestría con: " + championName);
    }
}
