package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedRoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecommendedRoleRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<RecommendedRoleModel> recommendedRole(String account1, String account2, String account3, String account4, String account5) throws NoDataException {
        String sql = "SELECT \"summonerName\",\"teamPosition\" AS recommendPosition ,COUNT(\"teamPosition\") AS gamesPlayed\n" +
                "FROM \"Match\" \n" +
                "WHERE \"summonerName\" =? OR \"summonerName\" =? OR \"summonerName\" =? OR \"summonerName\" =? OR \"summonerName\" =? \n" +
                "GROUP BY \"summonerName\",\"teamPosition\"";
        Object[] params = {account1, account2, account3, account4, account5};

        List<RecommendedRoleModel> recommendedRoleModel = jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(RecommendedRoleModel.class));
        if (recommendedRoleModel.isEmpty()) {
            throw new NoDataException();
        } else {
            return recommendedRoleModel;
        }
    }
}
