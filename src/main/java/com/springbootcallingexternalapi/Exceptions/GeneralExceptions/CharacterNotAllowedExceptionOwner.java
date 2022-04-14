package com.springbootcallingexternalapi.Exceptions.GeneralExceptions;

public class CharacterNotAllowedExceptionOwner extends Exception{

    public CharacterNotAllowedExceptionOwner (String owner, String owner2){
        super(owner + " or " + owner2 + " has characters not allowed");
    }
}
