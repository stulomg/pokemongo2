package com.springbootcallingexternalapi.Exceptions.OwnerExceptions;

import com.springbootcallingexternalapi.Models.OwnerModel;

public class OwnerAllreadyRegisterException extends Exception {
    public OwnerAllreadyRegisterException(OwnerModel owner) {
        super("LOS DATOS INGRESADOS PARA LA CUENTA " + owner.getName() + " YA EXISTEN POR INGRESAR INFORMACION DIFERENTE");
    }
}

