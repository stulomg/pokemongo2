package com.springbootcallingexternalapi.Exceptions.OwnerExceptions;

public class OwnerNotAllowedException extends Exception{
    public OwnerNotAllowedException(String owner){
        super ("The owner: " + owner + " is not allowed for this api");
    }
}
