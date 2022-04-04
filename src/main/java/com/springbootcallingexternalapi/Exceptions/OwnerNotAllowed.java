package com.springbootcallingexternalapi.Exceptions;

public class OwnerNotAllowed extends Exception{
    public OwnerNotAllowed (String owner){
        super ("The owner: " + owner + " is not allowed for this api");
    }
}
