package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions;

public class NoDataException extends Exception {

    public NoDataException(){
        super("No existen datos suficientes para realizar la consulta ");
    }
}
