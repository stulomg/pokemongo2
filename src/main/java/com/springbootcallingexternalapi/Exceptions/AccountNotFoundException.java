package com.springbootcallingexternalapi.Exceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String account) {
        super ("LA CUENTA "+ account + " NO FUE ENCONTRADA, POR FAVOR RECTIFICAR");
    }
}