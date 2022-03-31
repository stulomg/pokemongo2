package com.springbootcallingexternalapi.Exceptions;

public class QueueNotFoundException extends Exception {
    public QueueNotFoundException(String message) {
        super("NO FUE POSIBLE ENCONTRAR EL TIPO DE COLA: "+message);
    }
}
