package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;


import com.springbootcallingexternalapi.LeagueOfLegends.Models.GameDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MatchRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertMatchData(GameDataModel gamedata) {

        String sql = "INSERT INTO \"Match\" (\"championName\",\"summonerName\",\"win\",\"teamPosition\",\"individualPosition\",\"championPoints\") VALUES (?,?,?,?,?,?)";
        Object[] params = {gamedata.getChampionName(), gamedata.getSummonerName(), gamedata.isWin(), gamedata.getTeamPosition(), gamedata.getIndividualPosition(), gamedata.getChampionPoints()};
        jdbcTemplate.update(sql, params);
    }

}
