package com.springbootcallingexternalapi.Exceptions;

public class NameNotFoundException extends Exception{
    public NameNotFoundException (String name){
        super("EL NAME: " + name + " NO FUE ENCONTRADO, POR FAVOR RECTIFICAR");
    }
}
