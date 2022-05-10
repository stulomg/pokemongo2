package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OwnerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertOwner(OwnerModel owner){

        String sql = "INSERT INTO \"Owner\"(\"name\") VALUES (?)";
        Object[] params = {owner.getName()};
        jdbcTemplate.update(sql, params);

    }
}
