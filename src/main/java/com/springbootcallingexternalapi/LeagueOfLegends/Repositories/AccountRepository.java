package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.*;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

@Repository
public class AccountRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertAccount(AccountBaseModel account, String owner) throws AccountDataException, OwnerNotAllowedException, CharacterNotAllowedException {

        String sql = "INSERT INTO \"Account\" VALUES(?,?,?,?,?,?,?,?)";
        Object[] params = {account.getId(),
                account.getAccountId(), account.getPuuid(),
                account.getName().toLowerCase(Locale.ROOT),
                account.getProfileIconId(), account.getRevisionDate(),
                account.getSummonerLevel(),
                owner.toLowerCase(Locale.ROOT)};

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
        String sql = "DELETE FROM \"Account\" WHERE LOWER (name)=? AND LOWER (owner)=?";
        Object[] params = {account.toLowerCase(Locale.ROOT), owner.toLowerCase(Locale.ROOT)};

        if (isAlpha(owner) && isAlpha(account)) {
            int result = jdbcTemplate.update(sql, params);
            System.out.println(result);
            if (result == 0) {
                throw new AccountOrOwnerNotFoundException(owner, account);
            }
        } else throw new CharacterNotAllowedException(owner, account);


    }

    public List<AccountModel> retrieveAccountByOwner(String owner) throws CharacterNotAllowedException, OwnerNotFoundException {
        String sql = "SELECT * FROM \"Account\" WHERE LOWER (owner)=?";
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

    public void accountUpdate(AccountModel model) throws CharacterNotAllowedException, AccountNotFoundException {
        String sql = "UPDATE \"Account\" SET name=?, \"accountId\"=?, puuid=?, \"profileIconId\"=?, \"revisionDate\"=?," +
                " \"summonerLevel\"=?, owner=? WHERE id=?";
        Object[] params = {model.getName(), model.getAccountId(), model.getPuuid(), model.getProfileIconId(),
                model.getRevisionDate(), model.getSummonerLevel(), model.getOwner(), model.getId()};
        if (isAlpha(model.getName())) {
            int result = jdbcTemplate.update(sql, params);
            if (result == 0) {
                throw new AccountNotFoundException(model.getName());
            } else return;
        }
        throw new CharacterNotAllowedException(model.getName());
    }


    public List<AccountModel> retrieveAccountByAccountName(String account) throws CharacterNotAllowedException, AccountNotFoundException {
        String sql = "SELECT * FROM \"Account\" WHERE LOWER (name)=?";
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
