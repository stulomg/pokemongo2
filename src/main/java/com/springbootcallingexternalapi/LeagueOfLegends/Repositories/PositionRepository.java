package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Locale;

@Repository
public class PositionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long retrievePositionIdByPositionName(String positionName)  {
        String sql = "SELECT id FROM \"Position\" WHERE LOWER(\"namePosition\")=?;";
        Object[] params = {positionName.toLowerCase(Locale.ROOT)};

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }
}
