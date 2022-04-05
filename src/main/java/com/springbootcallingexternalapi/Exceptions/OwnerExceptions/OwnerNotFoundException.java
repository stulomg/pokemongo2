package com.springbootcallingexternalapi.Exceptions.OwnerExceptions;

public class OwnerNotFoundException extends Exception{
    public OwnerNotFoundException(String owner) {
        super ("EL OWNER "+ owner + " NO FUE ENCONTRADO, POR FAVOR RECTIFICAR");
    }
}
