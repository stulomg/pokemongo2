package com.springbootcallingexternalapi.Exceptions.MostPopularExceptions;

public class DBException extends Exception{
    public DBException(){
        super("ERROR EN LA CONEXION CON LA BASE DE DATOS");
    }
}
