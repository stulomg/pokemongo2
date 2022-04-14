package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions;

public class DBException extends Exception{
    public DBException(){
        super("ERROR EN LA CONEXION CON LA BASE DE DATOS");
    }
}
