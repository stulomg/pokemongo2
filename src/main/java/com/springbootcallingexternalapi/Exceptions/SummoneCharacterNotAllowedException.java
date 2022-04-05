package com.springbootcallingexternalapi.Exceptions;

public class SummoneCharacterNotAllowedException extends Exception {
    public SummoneCharacterNotAllowedException(String summonerName){

        super(summonerName + " has characters not allowed");
    }
}

