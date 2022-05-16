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

    public List<RecommendedRoleModel> recommendedRole(Integer account1, Integer account2, Integer account3, Integer account4, Integer account5) throws NoDataException {
        String sql = "SELECT \"account\",\"position\" AS recommendPosition ,COUNT(\"position\") AS gamesPlayed\n" +
                "        \t\tFROM \"MatchHistory\"\n" +
                "                WHERE \"account\" IN(?,?,?,?,?)\n" +
                "                GROUP BY \"account\",\"position\"\n" +
                "\t\t\t\tORDER BY COUNT(\"position\") DESC ";
        Object[] params = {account1, account2, account3, account4, account5};

        List<RecommendedRoleModel> recommendedRoleModel = jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(RecommendedRoleModel.class));
        if (recommendedRoleModel.isEmpty()) {
            throw new NoDataException();
        } else {
            return recommendedRoleModel;
        }
    }
}
