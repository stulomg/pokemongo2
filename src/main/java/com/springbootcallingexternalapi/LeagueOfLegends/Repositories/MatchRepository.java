package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameInfoBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.GameDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Scan repository class.
 */
@Repository
public class MatchRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /**
   * Insert individual match date in to the db.
   */
  public void insertIndividualMatchData(GameDataModel gamedata, Integer accountId,
      Integer positionId, Integer championId) {

    String sql = "INSERT INTO \"MatchHistory\"(account, \"position\", champion,"
        + " \"championPoints\", win)VALUES (?, ?, ?, ?, ?);";
    Object[] params = {accountId, positionId, championId, gamedata.getChampionPoints(),
        gamedata.isWin()};
    jdbcTemplate.update(sql, params);
  }

  /**
   * Insert full match date in to the db.
   */
  public void insertFullMatchData(CurrentGameInfoBaseModel gameModel) {
    String sql = "INSERT INTO \"FullMatchHistory\"(\"mapId\", \"gameMode\", \"gameType\","
        + "\"participants\")VALUES (?, ?, ?, ?);";
    Object[] params = {gameModel.getMapId(), gameModel.getGameMode(), gameModel.getGameType(),
        gameModel.getParticipants()};
    jdbcTemplate.update(sql, params);
  }
}