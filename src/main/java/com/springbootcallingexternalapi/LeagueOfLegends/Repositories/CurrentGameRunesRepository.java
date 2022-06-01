package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameInfoRuneModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CurrentGameRunesRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void insertRunes(CurrentGameInfoRuneModel currentGameInfoRuneModel) {

    String sql = "INSERT INTO \"CurrentGameRunes\" (\"participants\") VALUES(ARRAY['{?,?,?,?,?,?,?,?,?}']::jsonb[])";
    Object[] params = {
        currentGameInfoRuneModel.getParticipants()

    };
    jdbcTemplate.update(sql, params);
  }
}
