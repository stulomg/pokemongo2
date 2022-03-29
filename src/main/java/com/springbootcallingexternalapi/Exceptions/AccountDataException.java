package com.springbootcallingexternalapi.Exceptions;
import com.springbootcallingexternalapi.Models.AccountBaseModel;

public class AccountDataException extends Exception{
    public AccountDataException (AccountBaseModel account){
        super ("LOS DATOS INGRESADOS PARA LA CUENTA "+ account.getName() +" NO SON VALIDOS, POR FAVOR RECTIFICAR");
    }
}
