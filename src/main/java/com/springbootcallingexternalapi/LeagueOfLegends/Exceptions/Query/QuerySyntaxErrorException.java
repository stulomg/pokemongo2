package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query;

public class QuerySyntaxErrorException extends  Exception{
    public QuerySyntaxErrorException(String criteria) {
        super("Error in the syntax of the query : "+criteria+", check the data entered");
    }
}
