package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/** Scan repository class.*/
@Repository
public class RelationshipRepository {

  @Autowired
  AccountRepository accountRepository;
  @Autowired
  JdbcTemplate jdbcTemplate;

  /** Get players matched.*/
  public List<String> getPlayersMatched(String account)
      throws CharacterNotAllowedException, AccountNotFoundException {
    Integer accountId = Math.toIntExact(accountRepository.retrieveAccountIdByAccountName(account));
    String sql = "SELECT \"id_Jugador\" FROM \"Account_Jugador\" WHERE \"id_Account\"=?";
    Object[] params = {accountId};
    List<String> listPlayersMatched = jdbcTemplate.query(sql, params,
        BeanPropertyRowMapper.newInstance(String.class));
    return listPlayersMatched;
  }
}