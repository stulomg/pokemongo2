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
    public void deleteAccount (String owner, String nombre){
        String sql = "DELETE FROM \"Accounts\" WHERE name=? AND owner=?";
        Object[] params = {nombre, owner};
        int result = jdbcTemplate.update(sql, params);

        if (result>  0) {
            System.out.println("Delete successfully.");
        }
    }
}

