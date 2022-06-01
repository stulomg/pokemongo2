package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerAlreadyExists;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Scan repository class.
 */
@Repository
public class OwnerRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /**
   * Insert new owner in the db.
   */
  public List<OwnerModel> insertOwner(OwnerModel owner)
      throws OwnerAlreadyExists, CharacterNotAllowedException {
    String sql = "INSERT INTO \"Owner\"(\"name\") VALUES (?)";
    Object[] params = {owner.getName().toLowerCase(Locale.ROOT)};
    if (isAlpha(owner.getName())) {
      try {
        jdbcTemplate.update(sql, params);
      } catch (DataAccessException e) {
        throw new OwnerAlreadyExists();
      }
    } else {
      throw new CharacterNotAllowedException(owner.getName());
    }
    return null;
  }

  /**
   * Retrieve owner id by owner name.
   */
  public Long retrieveOwnerIdByOwnerName(String owner)
      throws CharacterNotAllowedException, OwnerNotFoundException {
    String sql = "SELECT \"id\" FROM \"Owner\" WHERE LOWER(\"name\")=?";
    Object[] params = {owner};
    if (isAlpha(owner)) {
      try {
        return jdbcTemplate.queryForObject(sql, params, Long.class);
      } catch (EmptyResultDataAccessException e) {
        throw new OwnerNotFoundException(owner);
      }
    } else {
      throw new CharacterNotAllowedException(owner);
    }
  }
}