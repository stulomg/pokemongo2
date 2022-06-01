package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/** Scan repository class.*/
@Repository
public class ChampionRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /** Retrieve champion id by champion name.*/
  public Long retrieveChampionIdByChampionName(String championName)
      throws ChampionNotFoundException, CharacterNotAllowedException {
    String sql = "SELECT \"ChampionId\" FROM \"Champion\" WHERE LOWER (\"ChampionName\")=?";
    Object[] params = {championName.toLowerCase(Locale.ROOT)};
    if (isAlpha(championName)) {
      try {
        return jdbcTemplate.queryForObject(sql, params, Long.class);
      } catch (EmptyResultDataAccessException e) {
        throw new ChampionNotFoundException(championName);
      }
    } else {
      throw new CharacterNotAllowedException(championName);
    }
  }
}