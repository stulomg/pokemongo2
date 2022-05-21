package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions;

public class NoDataException extends Exception {
    public NoDataException() {
        super("There is not enough data to perform the query");
    }
}