package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.*;
import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.SummonerNotFoundException;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.springbootcallingexternalapi.Util.AlphaVerifier.isAlpha;

@Repository
public class LeagueRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertLeagueInfo(LeagueInfoModel leagueInfoModel) throws AccountNotFoundException, CharacterNotAllowedException, AccountDataException {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        String sql = "INSERT INTO \"LeagueInfo\" VALUES(?,?,?,?,?,?,?)";
        Object[] params = {date,leagueInfoModel.getLeagueId(),leagueInfoModel.getQueueType(),leagueInfoModel.getTier(),leagueInfoModel.getRank(),leagueInfoModel.getSummonerName(),leagueInfoModel.getLeaguePoints()};
        if (isAlpha(leagueInfoModel.getSummonerName())) {
            try {
                jdbcTemplate.update(sql, params);
            } catch (DataAccessException e) {
                throw new AccountDataException(leagueInfoModel);
            }
        }else throw new CharacterNotAllowedException(leagueInfoModel.getSummonerName());
    }

    public List<LeagueInfoModel> divisionHistory(String account) throws CharacterNotAllowedException, AccountNotFoundException {
        String sql = "SELECT * FROM \"LeagueInfo\" WHERE \"summonerName\"=? ORDER BY date DESC LIMIT 20";
        Object[] params = {account};

        if (isAlpha(account)) {
            List<LeagueInfoModel> listOfLeagues = jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(LeagueInfoModel.class));
            if (listOfLeagues.size() == 0) {
                throw new AccountNotFoundException(account);
            } else return listOfLeagues;
        }
        throw new CharacterNotAllowedException(account);
    }
}
