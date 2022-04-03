package com.springbootcallingexternalapi.Exceptions;

public class CharacterNotAllowedException extends Exception{
    public CharacterNotAllowedException(String name){
        super(name + " has characters not allowed");
    }
}
