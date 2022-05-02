package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions;

public class DBException extends Exception {
    public DBException() {
        super("ERROR CONNECTING WITH THE DATABASE");
    }
}
