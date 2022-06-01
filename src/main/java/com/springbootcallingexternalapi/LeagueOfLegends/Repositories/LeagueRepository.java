package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedExceptionOwner;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.LeagueDataNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MaxDivisionModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/** Scan repository class.*/
@Repository
public class LeagueRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /** Insert the league info in to the db.*/
  public void insertLeagueInfo(LeagueInfoModel leagueInfoModel, Integer accountId, Integer ownerId)
      throws AccountDataException {
    String sql = "INSERT INTO \"LeagueHistory\"(date, leagueid, \"queueType\", tier, rank, "
        + "\"leaguePoints\", \"Elo\", account, owner) VALUES (?,?,?,?,?,?,?,?,?);";
    Object[] params = {
        leagueInfoModel.getDate(),
        leagueInfoModel.getLeagueId(),
        leagueInfoModel.getQueueType(),
        leagueInfoModel.getTier(),
        leagueInfoModel.getRank(),
        leagueInfoModel.getLeaguePoints(),
        leagueInfoModel.getElo(),
        accountId,
        ownerId
    };
    try {
      jdbcTemplate.update(sql, params);
    } catch (DataAccessException e) {
      throw new AccountDataException(leagueInfoModel);
    }
  }

  /** Select the division history from the db.*/
  public List<LeagueInfoModel> divisionHistory(String account, Integer accountId)
      throws CharacterNotAllowedException, LeagueDataNotFoundException {
    String sql = "SELECT * FROM \"LeagueHistory\" WHERE \"account\" =? ORDER BY"
        + " \"date\" DESC LIMIT 20;";
    Object[] params = {accountId};
    if (isAlpha(account)) {
      List<LeagueInfoModel> listOfLeagues = jdbcTemplate.query(sql, params,
          BeanPropertyRowMapper.newInstance(LeagueInfoModel.class));
      if (listOfLeagues.size() == 0) {
        throw new LeagueDataNotFoundException(account);
      } else {
        return listOfLeagues;
      }
    }
    throw new CharacterNotAllowedException(account);
  }

  /** Select the maximum division from the information in the database.*/
  public List<MaxDivisionModel> getMaxDivision(String owner, String owner2, Integer ownerId,
      Integer owner2Id) throws OwnerNotFoundException, CharacterNotAllowedExceptionOwner {
    String sql = "SELECT  \"account\", \"tier\",\"rank\",\"date\" FROM \"LeagueHistory\" "
        + "WHERE owner =? or owner =? GROUP BY \"account\", \"tier\", \"rank\",\"date\" "
        + "ORDER BY  MAX (\"Elo\") DESC  LIMIT 1";
    Object[] params = {ownerId, owner2Id};
    if (isAlpha(owner) && isAlpha(owner2)) {
      List<MaxDivisionModel> infoList = jdbcTemplate.query(sql, params,
          BeanPropertyRowMapper.newInstance(MaxDivisionModel.class));
      if (infoList.isEmpty()) {
        throw new OwnerNotFoundException(owner, owner2);
      }
      return infoList;
    } else {
      throw new CharacterNotAllowedExceptionOwner(owner, owner2);
    }
  }
}