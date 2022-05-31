package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query;

public class QueryNoDataException extends Exception{
    public QueryNoDataException() {
        super("There are currently no queries stored");
    }
}
