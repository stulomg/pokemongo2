package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashChampionModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashRoleModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashWinRateModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Scan repository class.
 */
@Repository
public class RecommendedClashRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  /**
   * Recommend a role.
   */
  public List<RecommendedClashRoleModel> recommendedRole(ArrayList accounts)
      throws NoDataException {
    String sql =
        "SELECT \"account\",\"position\" AS recommendPosition ,COUNT(\"position\") AS gamesPlayed\n"
            + "FROM \"MatchHistory\"\n"
            + "WHERE \"account\" IN(?,?,?,?,?)\n"
            + "GROUP BY \"account\",\"position\"\n"
            + "ORDER BY COUNT(\"position\") DESC ";
    Object[] params = {accounts.get(0), accounts.get(1), accounts.get(2), accounts.get(3),
        accounts.get(4)};
    List<RecommendedClashRoleModel> recommendedClashRoleModel = jdbcTemplate.query(sql, params,
        BeanPropertyRowMapper.newInstance(RecommendedClashRoleModel.class));
    if (recommendedClashRoleModel.isEmpty()) {
      throw new NoDataException();
    } else {
      return recommendedClashRoleModel;
    }
  }

  /**
   * Recommend a champion.
   */
  public List<RecommendedClashChampionModel> recommendChampion(ArrayList accounts)
      throws NoDataException {
    String sql =
        "SELECT account,champion ,COUNT(champion)as gamesplayed,"
            + "MAX(\"championPoints\")as maxmastery\n"
            +
            "FROM \"MatchHistory\" \n"
            + "WHERE account in (?,?,?,?,?)\n"
            + "GROUP BY account,champion \n"
            + "ORDER BY account,champion;";
    Object[] params = {accounts.get(0), accounts.get(1), accounts.get(2), accounts.get(3),
        accounts.get(4)};
    List<RecommendedClashChampionModel> recommendedClashChampionModel = jdbcTemplate.query(sql,
        params, BeanPropertyRowMapper.newInstance(RecommendedClashChampionModel.class));
    if (recommendedClashChampionModel.isEmpty()) {
      throw new NoDataException();
    } else {
      return recommendedClashChampionModel;
    }
  }

  /**
   * Recommend clash win rate.
   */
  public List<RecommendedClashWinRateModel> championWin(ArrayList accounts) throws NoDataException {
    String sql = "SELECT account, champion ,COUNT(\"win\")as win\n"
        + "FROM \"MatchHistory\" \n"
        + "WHERE \"win\" = true AND account in (?,?,?,?,?)\n"
        + "GROUP BY account, champion \n"
        + "ORDER BY account, champion";
    Object[] params = {accounts.get(0), accounts.get(1), accounts.get(2), accounts.get(3),
        accounts.get(4)};
    List<RecommendedClashWinRateModel> recommendedClashWinModel = jdbcTemplate.query(sql, params,
        BeanPropertyRowMapper.newInstance(RecommendedClashWinRateModel.class));
    if (recommendedClashWinModel.isEmpty()) {
      throw new NoDataException();
    } else {
      return recommendedClashWinModel;
    }
  }
}