package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Models.AccountBaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void insertAccount (AccountBaseModel account, String owner){
        String sql = "INSERT INTO \"Accounts\" VALUES ('"+ account.getId()+"', '"+account.getAccountId()+"', '"+account.getPuuid()+"', '"+account.getName()+"', "
                + account.getProfileIconId()+", "+account.getRevisionDate()+", "+account.getSummonerLevel()+", '"+owner+"');";
        jdbcTemplate.update(sql);
    }
}

