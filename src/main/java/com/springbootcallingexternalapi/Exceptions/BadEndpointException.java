package com.springbootcallingexternalapi.Exceptions;

public class BadEndpointException extends Exception{
    public BadEndpointException(){
        super("LA SOLICITUD NO PUDO SER REALIZADA, POR FAVOR VERIFIQUE LA URL");
    }
}
