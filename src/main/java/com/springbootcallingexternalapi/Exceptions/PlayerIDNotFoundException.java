package com.springbootcallingexternalapi.Exceptions;
public class OwnerNotFoundException extends Exception {
    public OwnerNotFoundException(String account) {
        super ("EL PROPIETARIO "+ account + " NO FUE ENCONTRADO, POR FAVOR RECTIFICAR");
    }
}
