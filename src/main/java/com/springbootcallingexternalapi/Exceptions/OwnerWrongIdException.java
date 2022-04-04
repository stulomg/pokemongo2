package com.springbootcallingexternalapi.Exceptions;

import com.springbootcallingexternalapi.Models.OwnerModel;

public class OwnerWrongIdException extends Exception{
    public OwnerWrongIdException(OwnerModel owner) {
        super ("EL OWNER "+ owner + " NO FUE ENCONTRADO, POR FAVOR RECTIFICAR");
    }
}

