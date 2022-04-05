package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.*;
import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Exceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.SummonerNotFoundException;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
    Logger logger = LoggerFactory.getLogger(AccountRepository.class);

    public void insertLeagueInfo(LeagueInfoModel leagueInfoModel) throws SummonerNotFoundException, CharacterNotAllowedException {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        String sql = "INSERT INTO \"LeagueInfo\" VALUES(?,?,?,?,?,?,?)";
        Object[] params = {date,leagueInfoModel.getLeagueId(),leagueInfoModel.getQueueType(),leagueInfoModel.getTier(),leagueInfoModel.getRank(),leagueInfoModel.getSummonerName(),leagueInfoModel.getLeaguePoints()};
        if (isAlpha(leagueInfoModel.getSummonerName())) {
            try {
                jdbcTemplate.update(sql, params);
            } catch (DataAccessException e) {
                logger.info(e.getMessage());
                throw new SummonerNotFoundException(leagueInfoModel);
            }
        }else throw new CharacterNotAllowedException(leagueInfoModel.getSummonerName());
    }

    public List<LeagueInfoModel> divisionHistory(String summonerName) throws SummoneCharacterNotAllowedException, SummonernameNotFoundException {
        String sql = "SELECT * FROM (SELECT * FROM \"LeagueInfo\" WHERE \"summonerName\"=? ORDER BY date DESC LIMIT 20) sub \n" + "ORDER BY date ASC;";
        Object[] params = {summonerName};

        if (isAlpha(summonerName)) {
            List<LeagueInfoModel> listOfLeagues = jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(LeagueInfoModel.class));
            if (listOfLeagues.size() == 0) {
                throw new SummonernameNotFoundException(summonerName);
            } else return listOfLeagues;
        }
        throw new SummoneCharacterNotAllowedException(summonerName);
    }
}
