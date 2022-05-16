package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query;

public class QuerySyntaxError extends  Exception{
    public QuerySyntaxError(String criterio) {
        super("Error in the syntax of the query : "+criterio+", check the data entered");
    }
}
