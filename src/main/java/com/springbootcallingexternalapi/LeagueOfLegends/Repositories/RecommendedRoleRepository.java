package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MostPopularModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecommendedRoleRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<RecommendedRole> recommendedRole(String account1,String account2,String account3,String account4,String account5) throws NoDataException {
        String sql = "SELECT \"summonerName\",\"teamPosition\" ,COUNT(\"teamPosition\") AS mRole\n" +
                "FROM \"Match\" \n" +
                "WHERE \"summonerName\" =? OR \"summonerName\" =? OR \"summonerName\" =? OR \"summonerName\" =? OR \"summonerName\" =? \n" +
                "GROUP BY \"summonerName\",\"teamPosition\" \n" +
                "ORDER BY COUNT(\"summonerName\") ";
        Object[] params = {account1, account2, account3, account4, account5};

        List<RecommendedRole> recommendedRole = jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(RecommendedRole.class));
        if (recommendedRole.isEmpty()) {
            throw new NoDataException();
        } else {
            return recommendedRole;
        }
    }
}
