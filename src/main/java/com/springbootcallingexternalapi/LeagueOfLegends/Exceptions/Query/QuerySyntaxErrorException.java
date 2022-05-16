package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query;

public class QuerySyntaxErrorException extends  Exception{
    public QuerySyntaxErrorException(String criterio) {
        super("Error in the syntax of the query : "+criterio+", check the data entered");
    }
}
