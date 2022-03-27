package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;

import java.awt.print.Book;
import java.util.List;

@Repository
public class AccountRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertAccount(AccountBaseModel account, String owner) throws AccountDataException {
        String sql = "INSERT INTO \"Accounts\" VALUES ('" + account.getId() + "', '" + account.getAccountId() + "', '" + account.getPuuid() + "', '" + account.getName() + "', "
                + account.getProfileIconId() + ", " + account.getRevisionDate() + ", " + account.getSummonerLevel() + ", '" + owner + "');";
        try{

            jdbcTemplate.update(sql);

        }catch (DataAccessException e){
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
}

