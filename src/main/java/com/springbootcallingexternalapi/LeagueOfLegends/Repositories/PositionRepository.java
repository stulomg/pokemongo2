package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Position.PositionNotFoundException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/** Scan repository class.*/
@Repository
public class PositionRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /** Retrieve position id by position name.*/
  public Long retrievePositionIdByPositionName(String positionName)
      throws CharacterNotAllowedException, PositionNotFoundException {
    String sql = "SELECT id FROM \"Position\" WHERE LOWER(\"namePosition\")=?;";
    Object[] params = {positionName.toLowerCase(Locale.ROOT)};
    if (isAlpha(positionName)) {
      try {
        return jdbcTemplate.queryForObject(sql, params, Long.class);
      } catch (EmptyResultDataAccessException e) {
        throw new PositionNotFoundException(positionName);
      }
    } else {
      throw new CharacterNotAllowedException(positionName);
    }
  }
}