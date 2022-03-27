package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;

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
}

