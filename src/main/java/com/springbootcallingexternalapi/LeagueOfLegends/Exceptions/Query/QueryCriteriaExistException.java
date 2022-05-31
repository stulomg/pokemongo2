package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query;

public class QueryCriteriaExistException extends Exception{
    public QueryCriteriaExistException(String criteria) {
        super("The criteria: "+criteria+", is already registered, please use another name");
    }
}
