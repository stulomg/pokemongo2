package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query;

public class QueryFilterNoDataException extends Exception {
    public QueryFilterNoDataException(String criteria) {
        super("There is no query with the criteria: "+criteria);
    }
}
