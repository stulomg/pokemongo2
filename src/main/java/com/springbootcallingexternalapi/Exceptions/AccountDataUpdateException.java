package com.springbootcallingexternalapi.Exceptions;

import com.springbootcallingexternalapi.Models.AccountModel;

public class AccountDataUpdateException extends Exception{
    public AccountDataUpdateException (AccountModel account){
        super("LOS DATOS DE LA CUENTA "+account.getName()+" NO PUEDEN SER ACTUALIZADOS");    }
}
