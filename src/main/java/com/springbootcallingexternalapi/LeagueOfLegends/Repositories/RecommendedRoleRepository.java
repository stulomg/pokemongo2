package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedRoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

@Repository
public class RecommendedRoleRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<RecommendedRoleModel> recommendedRole(String account1, String account2, String account3, String account4, String account5) throws NoDataException {
        String sql = "SELECT \"summonerName\",\"teamPosition\" AS recommendPosition ,COUNT(\"teamPosition\") AS gamesPlayed\n" +
                "FROM \"Match\" \n" +
                "WHERE \"summonerName\" =? OR \"summonerName\" =? OR \"summonerName\" =? OR \"summonerName\" =? OR \"summonerName\" =? \n" +
                "GROUP BY \"summonerName\",\"teamPosition\"";
        Object[] params = {account1.toLowerCase(Locale.ROOT), account2.toLowerCase(Locale.ROOT), account3.toLowerCase(Locale.ROOT), account4.toLowerCase(Locale.ROOT), account5.toLowerCase(Locale.ROOT)};

        List<RecommendedRoleModel> recommendedRoleModel = jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(RecommendedRoleModel.class));
        if (recommendedRoleModel.isEmpty()) {
            throw new NoDataException();
        } else {
            return recommendedRoleModel;
        }
    }
}
