package com.springbootcallingexternalapi.Exceptions;

public class AccountOrOwnerNotFoundException extends Exception{
    public AccountOrOwnerNotFoundException (String account , String owner){
        super("LA CUENTA: " + account +", VINCULADA AL USUARIO: " + owner +" NO FUE ENCONTRADA, PORFAVOR RECTIFICAR.");
    }
}
