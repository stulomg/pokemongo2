package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameInfoRuneModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/** Scan repository class.*/

@Repository
public class CurrentGameRunesRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  /** Scan repository class.*/

  public void insertRunes (CurrentGameInfoRuneModel currentGameInfoRuneModel)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    String sql = "INSERT INTO \"CurrentGameRunes\" (\"participants\") values (?::json)";

    Object[] params = {
        currentGameInfoRuneModel.getParticipants()
    };
    String jsonString = mapper.writeValueAsString(params);

    jdbcTemplate.update(sql, jsonString);
  }
}

