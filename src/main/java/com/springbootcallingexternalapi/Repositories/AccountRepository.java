package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.*;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.AccountModel;
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

    public void insertAccount(AccountBaseModel account, String owner) throws AccountDataException, OwnerNotAllowedException, CharacterNotAllowedException {

        String sql = "INSERT INTO \"Accounts\" VALUES(?,?,?,?,?,?,?,?)";
        Object[] params = {account.getId(), account.getAccountId(), account.getPuuid(), account.getName().toLowerCase(Locale.ROOT), account.getProfileIconId(), account.getRevisionDate(), account.getSummonerLevel(), owner.toLowerCase(Locale.ROOT)};
        if (isAlpha(owner)) {
            try {
                if (owner.equalsIgnoreCase("kusi") || owner.equalsIgnoreCase("stul")) {
                    jdbcTemplate.update(sql, params);
                } else throw new OwnerNotAllowedException(owner);
            } catch (DataAccessException e) {
                throw new AccountDataException(account);
            }
        } else throw new CharacterNotAllowedException(owner);
    }

    public void deleteAccount(String owner, String account) throws AccountOrOwnerNotFoundException, CharacterNotAllowedException {
        String sql = "DELETE FROM \"Accounts\" WHERE LOWER (name)=? AND LOWER (owner)=?";
        Object[] params = {account.toLowerCase(Locale.ROOT), owner.toLowerCase(Locale.ROOT)};

        if (isAlpha(owner, account)) {
            int result = jdbcTemplate.update(sql, params);
            System.out.println(result);
            if (result == 0) {
                throw new AccountOrOwnerNotFoundException(account, owner);
            }
        } else throw new CharacterNotAllowedException(owner, account);


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

    //Agregar excepcion para el error NOTNULL en los campos de la base de datos
    public void accountUpdate(AccountModel model) {
        String sql = "UPDATE \"Accounts\" SET name=?, \"accountId\"=?, puuid=?, \"profileIconId\"=?, \"revisionDate\"=?," +
                " \"summonerLevel\"=?, owner=? WHERE id=?";
        Object[] params = {model.getName(), model.getAccountId(), model.getPuuid(), model.getProfileIconId(),
                model.getRevisionDate(), model.getSummonerLevel(), model.getOwner(), model.getId()};

        jdbcTemplate.update(sql, params);
    }


    public List<AccountModel> retrieveAccountByAccountName(String account) throws CharacterNotAllowedException, AccountNotFoundException {
        String sql = "SELECT * FROM \"Accounts\" WHERE LOWER (name)=?";
        Object[] params = {account};

        if (isAlpha(account)) {
            List<AccountModel> listAccounts = jdbcTemplate.query(sql, params,
                    BeanPropertyRowMapper.newInstance(AccountModel.class));
            if (listAccounts.size() == 0) {
                throw new AccountNotFoundException(account);
            } else return listAccounts;

        } else throw new CharacterNotAllowedException(account);
    }
}
