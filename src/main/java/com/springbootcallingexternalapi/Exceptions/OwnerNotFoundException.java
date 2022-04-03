package com.springbootcallingexternalapi.Exceptions;

public class OwnerNotFoundException extends Exception{
    public OwnerNotFoundException(String owner) {
        super ("EL OWNER "+ owner + " NO FUE ENCONTRADO, POR FAVOR RECTIFICAR");
    }
}
