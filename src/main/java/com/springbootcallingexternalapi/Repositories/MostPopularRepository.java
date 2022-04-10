package com.springbootcallingexternalapi.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MostPopularRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void retrieveMostPopularAccount(){
        String sql = "SELECT \"name\" COUNT(\"name\") AS \"value_occurrence\" " +
                "FROM \"Accounts\" GROUP BY \"name\" ORDER BY \"value_ocurrence\" DESC LIMIT 1;";
    }
}
