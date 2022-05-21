package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountExistsOrNotException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public void insertAccount(AccountBaseModel account, Integer owner) throws AccountDataException, AccountExistsOrNotException {
        String sql = "INSERT INTO \"Account\"(id, puuid, accountid, \"revisionDate\", \"owner\", name) VALUES (?, ?, ?, ?, ?, ?);";
        Object[] params = {
                account.getId(),
                account.getPuuid(),
                account.getAccountId(),
                account.getRevisionDate(),
                owner,
                account.getName().toLowerCase(Locale.ROOT)};
            try {
                jdbcTemplate.update(sql, params);
            }catch (DataIntegrityViolationException e){
                throw new AccountExistsOrNotException();
            }catch (DataAccessException e) {
                throw new AccountDataException(account);
            }
    }

    public void deleteAccount( String account,Integer ownerID) throws CharacterNotAllowedException, AccountNotFoundException {
        String sql = "DELETE FROM \"Account\" WHERE Lower(name) =? and \"owner\" =?;";
        Object[] params = {account.toLowerCase(Locale.ROOT), ownerID};
        if (isAlpha(account)) {
            int result = jdbcTemplate.update(sql, params);
            if (result == 0) {
                throw new AccountNotFoundException(account);
            }
        } else throw new CharacterNotAllowedException(account);
    }

    public List<AccountModel> retrieveAccountByOwner(String owner,Integer ownerID) throws CharacterNotAllowedException, OwnerNotFoundException {
        String sql = "SELECT * FROM \"Account\" WHERE  \"owner\" =?";
        Object[] params = {ownerID};
        if (isAlpha(owner)) {
            List<AccountModel> listAccounts = jdbcTemplate.query(sql, params,
                    BeanPropertyRowMapper.newInstance(AccountModel.class));
            if (listAccounts.size() == 0) {
                throw new OwnerNotFoundException(owner);
            } else return listAccounts;
        }
        throw new CharacterNotAllowedException(owner);
    }

    public void accountUpdate(AccountModel model,Integer ownerID) throws CharacterNotAllowedException, AccountNotFoundException {
        String sql = "UPDATE \"Account\" SET name=?, \"accountid\"=?, puuid=?,\"revisionDate\"=?," +
                " owner=? WHERE id=?";
        Object[] params = {model.getName().toLowerCase(Locale.ROOT), model.getAccountId(), model.getPuuid(),
                model.getRevisionDate(), ownerID, model.getId()};
        if (isAlpha(model.getName())) {
            int result = jdbcTemplate.update(sql, params);
            if (result == 0) {
                throw new AccountNotFoundException(model.getName());
            } else return;
        }
        throw new CharacterNotAllowedException(model.getName());
    }

    public void accountUpdateExisting(AccountBaseModel model,Integer owner) throws CharacterNotAllowedException, AccountNotFoundException {
        String sql = "UPDATE \"Account\" SET name=?, \"accountid\"=?, puuid=?,\"revisionDate\"=?," +
                " owner=? WHERE id=?";
        Object[] params = {model.getName().toLowerCase(Locale.ROOT), model.getAccountId(), model.getPuuid(),
                model.getRevisionDate(), owner, model.getId()};
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

    public Integer retrieveAccountIdByAccountName(String accountName) throws  CharacterNotAllowedException, AccountNotFoundException {
        String sql = "SELECT \"id_BD\" FROM \"Account\" WHERE LOWER(\"name\")=?;";
        Object[] params = {accountName.toLowerCase(Locale.ROOT)};
        if (isAlpha(accountName)) {
            try {
                return jdbcTemplate.queryForObject(sql, params, Integer.class);
            } catch (EmptyResultDataAccessException e) {
                throw new AccountNotFoundException(accountName);
            }
        } else throw new CharacterNotAllowedException(accountName);
    }
    public Long retrieveOwnerIdByAccount(String account) throws CharacterNotAllowedException, AccountNotFoundException {
        String sql = "SELECT owner FROM \"Account\" WHERE name =?;";
        Object[] params = {account};
        if (isAlpha(account)) {
            try {
                return jdbcTemplate.queryForObject(sql, params, Long.class);
            } catch (EmptyResultDataAccessException e) {
                throw new AccountNotFoundException(account);
            }
        } else throw new CharacterNotAllowedException(account);
    }
}