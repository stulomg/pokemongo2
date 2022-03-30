package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountDataUpdateException;
import com.springbootcallingexternalapi.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.Exceptions.SummonerIdNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(AccountRepository.class);

    public void insertAccount(AccountBaseModel account, String owner) throws AccountDataException {

        String sql = "INSERT INTO \"Accounts\" VALUES(?,?,?,?,?,?,?,?)";
        Object[] params = {account.getId(),account.getAccountId(),account.getPuuid(),account.getName(),account.getProfileIconId(),account.getRevisionDate(), account.getSummonerLevel(),owner};
        try{
            jdbcTemplate.update(sql,params);
        }catch (DataAccessException e){
            logger.info(e.getMessage());
            throw new AccountDataException(account);
        }
    }


    public void deleteAccount(String owner, String nombre) throws AccountOrOwnerNotFoundException {
        String sql = "DELETE FROM \"Accounts\" WHERE name=? AND owner=?";
        Object[] params = {nombre, owner};


        int result = jdbcTemplate.update(sql, params);

        if (result == 0) {
            throw new AccountOrOwnerNotFoundException(nombre, owner);
        }
    }

    public List<AccountModel> retrieveAccountByOwner(String owner) {
        String sql = "SELECT * FROM \"Accounts\" WHERE owner=?" ;
        Object[] params = {owner};

        List<AccountModel> listAccounts = jdbcTemplate.query(sql,params,
                BeanPropertyRowMapper.newInstance(AccountModel.class));

        return listAccounts;
    }

    public void accountUpdate(AccountModel model) throws AccountDataUpdateException {
        String sql = "UPDATE \"Accounts\" SET name=?, \"accountId\"=?, puuid=?, \"profileIconId\"=?, \"revisionDate\"=?, \"summonerLevel\"=?, owner=? WHERE id=?";
        Object[] params = {model.getName(), model.getAccountId(), model.getPuuid(), model.getProfileIconId(), model.getRevisionDate(), model.getSummonerLevel(), model.getOwner(), model.getId()};
        int result = jdbcTemplate.update(sql, params);

        if (result == 0) {
            throw new AccountDataUpdateException(model);
        }

    }

    public List<AccountModel> retrieveAccountByName(String name) {
        String sql = "SELECT * FROM \"Accounts\" WHERE name=?" ;
        Object[] params = {name};

        List<AccountModel> listAccounts = jdbcTemplate.query(sql,params,
                BeanPropertyRowMapper.newInstance(AccountModel.class));

        return listAccounts;

    }
    public List<LeagueInfoModel> retrieveLeagueInfoByName(String name){
        String sql = "SELECT * FROM \"Accounts\" WHERE name=?" ;
        Object[] params = {name};

        List<LeagueInfoModel> listAccounts = jdbcTemplate.query(sql,params,
                BeanPropertyRowMapper.newInstance(LeagueInfoModel.class));

        return listAccounts;
    }

    public void insertLeagueInfo(LeagueInfoModel account, String owner) throws PlayerIDNotFoundException {
        String sql = "INSERT INTO \"Accounts\" VALUES(?,?,?,?,?,?,?,?)";

        try{
            jdbcTemplate.update(sql);

        }catch (DataAccessException e){
            throw new PlayerIDNotFoundException(account);
        }
    }
}
