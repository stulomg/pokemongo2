package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameInfoRuneModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameRunesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RunesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertRunes (CurrentGameInfoRuneModel currentGameInfoRuneModel){

        String sql = "INSERT INTO \"CurrentGameRunes\" (\"participants\") VALUES(?)";
        Object[] params = {
                currentGameInfoRuneModel.getParticipants()
                
        };
        jdbcTemplate.update(sql, params);
    }
}
