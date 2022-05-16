package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class RelationshipRepo {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> getPlayersMatched (String account) throws CharacterNotAllowedException, AccountNotFoundException {

        Integer AccoundId = Math.toIntExact(accountRepository.retrieveAccountIdByAccountName(account));
        String sql = "SELECT \"id_Jugador\" FROM \"Account_Jugador\" WHERE \"id_Account\"=?";
        Object[] params = {AccoundId};
        List<String> listPayersMatched = jdbcTemplate.query(sql, params,
                BeanPropertyRowMapper.newInstance(String.class));
        return listPayersMatched;
    }
}
