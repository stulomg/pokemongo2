package com.springbootcallingexternalapi.Exceptions;

public class ChampionNotFoundException extends Exception{
    public ChampionNotFoundException (String championName){
        super("El campeón " + championName + " no fue encontrado");
    }
}


