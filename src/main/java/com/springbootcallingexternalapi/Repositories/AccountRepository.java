package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.*;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.AccountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

import static com.springbootcallingexternalapi.Util.AlphaVerifier.isAlpha;

@Repository
public class AccountRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(AccountRepository.class);

    public void insertAccount(AccountBaseModel account, String owner) throws AccountDataException, OwnerNotAllowed {

        String sql = "INSERT INTO \"Accounts\" VALUES(?,?,?,?,?,?,?,?)";
        Object[] params = {account.getId(), account.getAccountId(), account.getPuuid(), account.getName().toLowerCase(Locale.ROOT), account.getProfileIconId(), account.getRevisionDate(), account.getSummonerLevel(), owner.toLowerCase(Locale.ROOT)};
        try {
            if (owner.equalsIgnoreCase("kusi") || owner.equalsIgnoreCase("stul")) {
                jdbcTemplate.update(sql, params);
            } else throw new OwnerNotAllowed(owner);
        } catch (DataAccessException e) {
            throw new AccountDataException(account);
        }
    }

    public void deleteAccount(String owner, String account) throws AccountOrOwnerNotFoundException, CharacterNotAllowedException {
        String sql = "DELETE FROM \"Accounts\" WHERE LOWER (name)=? AND LOWER (owner)=?";
        Object[] params = {account, owner};


        if (isAlpha(owner, account)) {
            int result = jdbcTemplate.update(sql, params);

            if (result == 0) {
                throw new AccountOrOwnerNotFoundException(account, owner);
            }
        }
        throw new CharacterNotAllowedException(owner, account);


    }

    public List<AccountModel> retrieveAccountByOwner(String owner) throws CharacterNotAllowedException, OwnerNotFoundException {
        String sql = "SELECT * FROM \"Accounts\" WHERE LOWER (owner)=?";
        Object[] params = {owner};

        if (isAlpha(owner)) {

            List<AccountModel> listAccounts = jdbcTemplate.query(sql, params,
                    BeanPropertyRowMapper.newInstance(AccountModel.class));
            if (listAccounts.size() == 0) {
                throw new OwnerNotFoundException(owner);
            } else return listAccounts;
        }
        throw new CharacterNotAllowedException(owner);
    }

    public void accountUpdate(AccountModel model) {
        String sql = "UPDATE \"Accounts\" SET name=?, \"accountId\"=?, puuid=?, \"profileIconId\"=?, \"revisionDate\"=?, \"summonerLevel\"=?, owner=? WHERE id=?";
        Object[] params = {model.getName(), model.getAccountId(), model.getPuuid(), model.getProfileIconId(), model.getRevisionDate(), model.getSummonerLevel(), model.getOwner(), model.getId()};

        int result = jdbcTemplate.update(sql, params);
    }

    public List<AccountModel> retrieveAccountByName(String name) throws CharacterNotAllowedException, NameNotFoundException {
        String sql = "SELECT * FROM \"Accounts\" WHERE LOWER (name)=?";
        Object[] params = {name};

        if (isAlpha(name)) {
            List<AccountModel> listAccounts = jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(AccountModel.class));
            if (listAccounts.size() == 0) {
                throw new NameNotFoundException(name);
            } else return listAccounts;
        }
        throw new CharacterNotAllowedException(name);
    }
}
