package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MostPopularModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/** Scan repository class.*/

@Repository
public class MostPopularRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  /** Select the most popular account.*/

  public List<MostPopularModel> popularAccount() throws NoDataException {
    String sql =
        "SELECT  \"account\", \"champion\", (SELECT TO_CHAR(\"date\"::date,'Mon dd')"
            + " AS date FROM  \"LeagueHistory\" WHERE \"account\" = (SELECT \"account\" "
            + "FROM  \"LeagueHistory\" GROUP BY \"account\" ORDER BY COUNT( \"account\" ) "
            + "DESC LIMIT 1) GROUP BY \"date\"::date , \"account\" ORDER BY COUNT( \"date\" ) "
            + "DESC LIMIT 1)\n"
            + "FROM  \"MasteryHistory\"\n" + "WHERE date >= (now() - '1 month'::INTERVAL) "
            + "and \"account\" = (SELECT \"account\" FROM  \"LeagueHistory\" "
            + "GROUP BY \"account\" ORDER BY COUNT( \"account\" ) DESC LIMIT 1)\n"
            + "GROUP BY \"champion\",\"account\"\n" + "ORDER BY MAX(\"championPoints\")"
            + "- MIN(\"championPoints\") DESC LIMIT 1";
    List<MostPopularModel> popularAccount = jdbcTemplate.query(sql,
        BeanPropertyRowMapper.newInstance(MostPopularModel.class));
    if (popularAccount.isEmpty()) {
      throw new NoDataException();
    } else {
      return popularAccount;
    }
  }
}