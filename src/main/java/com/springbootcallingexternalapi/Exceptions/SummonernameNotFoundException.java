package com.springbootcallingexternalapi.Exceptions;


public class SummonernameNotFoundException extends Exception {
    public SummonernameNotFoundException(String SummonerName) {
        super ("NO SE HA ENCONTRADO EL SUMMONER: "+ SummonerName + " POR FAVOR RECTIFICAR");
    }
}

