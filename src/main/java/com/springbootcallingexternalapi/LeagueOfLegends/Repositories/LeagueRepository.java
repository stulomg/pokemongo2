package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedExceptionOwner;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

@Repository
public class LeagueRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertLeagueInfo(LeagueInfoModel leagueInfoModel, String owner) throws CharacterNotAllowedException, AccountDataException {

        String sql = "INSERT INTO \"LeagueInfo\" VALUES(?,?,?,?,?,?,?,?,?)";
        Object[] params = {leagueInfoModel.getDate(),
                leagueInfoModel.getLeagueId(),
                leagueInfoModel.getQueueType(),
                leagueInfoModel.getTier(),
                leagueInfoModel.getRank(),
                leagueInfoModel.getSummonerName(),
                leagueInfoModel.getLeaguePoints(),
                leagueInfoModel.getElo(),
                owner
        };

        if (isAlpha(leagueInfoModel.getSummonerName())) {
            try {
                jdbcTemplate.update(sql, params);
            } catch (DataAccessException e) {
                throw new AccountDataException(leagueInfoModel);
            }
        } else throw new CharacterNotAllowedException(leagueInfoModel.getSummonerName());
    }

    public List<LeagueInfoModel> divisionHistory(String account) throws CharacterNotAllowedException, AccountNotFoundException {
        String sql = "SELECT * FROM \"LeagueInfo\" WHERE \"summonerName\"=? ORDER BY \"date\" DESC LIMIT 20";
        Object[] params = {account};

        if (isAlpha(account)) {
            List<LeagueInfoModel> listOfLeagues = jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(LeagueInfoModel.class));
            if (listOfLeagues.size() == 0) {
                throw new AccountNotFoundException(account);
            } else return listOfLeagues;
        }
        throw new CharacterNotAllowedException(account);
    }

    public List<LeagueInfoModel> getMaxDivision(String owner, String owner2) throws OwnerNotFoundException, CharacterNotAllowedExceptionOwner {
        String sql = "SELECT  \"summonerName\", \"tier\",\"rank\",\"date\" FROM \"LeagueInfo\" WHERE owner =? or owner =? GROUP BY \"summonerName\", \"tier\", \"rank\",\"date\" ORDER BY  MAX (\"Elo\") DESC  LIMIT 1";
        Object[] params = {owner, owner2};

        if (isAlpha(owner) && isAlpha(owner2)) {
            List<LeagueInfoModel> infoList = jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(LeagueInfoModel.class));
            if (infoList.isEmpty()) throw new OwnerNotFoundException(owner, owner2);
            return infoList;
        } else throw new CharacterNotAllowedExceptionOwner(owner, owner2);
    }
}


