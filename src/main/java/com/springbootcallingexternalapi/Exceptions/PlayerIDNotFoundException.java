package com.springbootcallingexternalapi.Exceptions;
public class PlayerIDNotFoundException extends Exception {
    public PlayerIDNotFoundException(String account) {
        super ("EL PROPIETARIO "+ account + " NO FUE ENCONTRADO, POR FAVOR RECTIFICAR");
    }
}
