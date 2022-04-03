package com.springbootcallingexternalapi.Exceptions;

public class QueueNotFoundException extends Exception {
    public QueueNotFoundException(String message) {
        super("EL INVOCADOR NO TIENE HISTORIAL EN LA COLA: "+message+ ", O EL INVOCADOR HA SIDO INSERTADO INCORRECTAMENTE, PORFAVOR VERIFICAR");
    }
}
