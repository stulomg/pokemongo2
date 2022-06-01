package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountExistsOrNotException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundDbException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountModel;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/** Scan repository class.*/
@Repository
public class AccountRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /** Insert account in the DB.*/
  public void insertAccount(AccountBaseModel account, Integer owner)
      throws AccountDataException, AccountExistsOrNotException {
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
    } catch (DataIntegrityViolationException e) {
      throw new AccountExistsOrNotException();
    } catch (DataAccessException e) {
      throw new AccountDataException(account);
    }
  }

  /** Delete an account.*/
  public void deleteAccount(String account, Integer ownerId)
      throws CharacterNotAllowedException, AccountNotFoundException {
    String sql = "DELETE FROM \"Account\" WHERE Lower(name) =? and \"owner\" =?;";
    Object[] params = {account.toLowerCase(Locale.ROOT), ownerId};
    if (isAlpha(account)) {
      int result = jdbcTemplate.update(sql, params);
      if (result == 0) {
        throw new AccountNotFoundException(account);
      }
    } else {
      throw new CharacterNotAllowedException(account);
    }
  }

  /** Retrieve account by owner.*/
  public List<AccountModel> retrieveAccountByOwner(String owner, Integer ownerId)
      throws CharacterNotAllowedException, OwnerNotFoundException {
    String sql = "SELECT * FROM \"Account\" WHERE  \"owner\" =?";
    Object[] params = {ownerId};
    if (isAlpha(owner)) {
      List<AccountModel> listAccounts = jdbcTemplate.query(sql, params,
          BeanPropertyRowMapper.newInstance(AccountModel.class));
      if (listAccounts.size() == 0) {
        throw new OwnerNotFoundException(owner);
      } else {
        return listAccounts;
      }
    }
    throw new CharacterNotAllowedException(owner);
  }

  /** Update account.*/
  public void accountUpdate(AccountModel model, Integer ownerId)
      throws CharacterNotAllowedException, AccountNotFoundException {
    String sql = "UPDATE \"Account\" SET name=?, \"accountid\"=?, puuid=?,\"revisionDate\"=?,"
        + "" + " owner=? WHERE id=?";
    Object[] params = {model.getName().toLowerCase(Locale.ROOT), model.getAccountId(),
        model.getPuuid(),
        model.getRevisionDate(), ownerId, model.getId()};

    if (isAlpha(model.getName())) {
      int result = jdbcTemplate.update(sql, params);
      if (result == 0) {
        throw new AccountNotFoundException(model.getName());
      } else {
        return;
      }
    }
    throw new CharacterNotAllowedException(model.getName());
  }

  /** Update existing account.*/
  public void accountUpdateExisting(AccountBaseModel model, Integer owner)
      throws CharacterNotAllowedException, AccountNotFoundException {
    String sql = "UPDATE \"Account\" SET name=?, \"accountid\"=?, puuid=?,\"revisionDate\"=?,"
        + " owner=? WHERE id=?";
    Object[] params = {model.getName().toLowerCase(Locale.ROOT), model.getAccountId(),
        model.getPuuid(),
        model.getRevisionDate(), owner, model.getId()};
    if (isAlpha(model.getName())) {
      int result = jdbcTemplate.update(sql, params);
      if (result == 0) {
        throw new AccountNotFoundException(model.getName());
      } else {
        return;
      }
    }
    throw new CharacterNotAllowedException(model.getName());
  }

  /** Retrieve account by account name.*/
  public List<AccountModel> retrieveAccountByAccountName(String account)
      throws CharacterNotAllowedException, AccountNotFoundException {
    String sql = "SELECT * FROM \"Account\" WHERE LOWER (name)=?";
    Object[] params = {account};
    if (isAlpha(account)) {
      List<AccountModel> listAccounts = jdbcTemplate.query(sql, params,
          BeanPropertyRowMapper.newInstance(AccountModel.class));
      if (listAccounts.size() == 0) {
        throw new AccountNotFoundException(account);
      } else {
        return listAccounts;
      }
    } else {
      throw new CharacterNotAllowedException(account);
    }
  }

  /** Retrieve account id by account name.*/
  public Integer retrieveAccountIdByAccountName(String accountName)
      throws CharacterNotAllowedException, AccountNotFoundException {
    String sql = "SELECT \"id_BD\" FROM \"Account\" WHERE LOWER(\"name\")=?;";
    Object[] params = {accountName.toLowerCase(Locale.ROOT)};
    if (isAlpha(accountName)) {
      try {
        return jdbcTemplate.queryForObject(sql, params, Integer.class);
      } catch (EmptyResultDataAccessException e) {
        throw new AccountNotFoundException(accountName);
      }
    } else {
      throw new CharacterNotAllowedException(accountName);
    }
  }

  /** Retrieve owner id by account.*/
  public Long retrieveOwnerIdByAccount(String account)
      throws CharacterNotAllowedException, AccountNotFoundException {
    String sql = "SELECT owner FROM \"Account\" WHERE LOWER(name) =?;";
    Object[] params = {account.toLowerCase(Locale.ROOT)};
    if (isAlpha(account)) {
      try {
        return jdbcTemplate.queryForObject(sql, params, Long.class);
      } catch (EmptyResultDataAccessException e) {
        throw new AccountNotFoundException(account);
      }
    } else {
      throw new CharacterNotAllowedException(account);
    }
  }

  /** Retrieve riot id by account.*/
  public String retrieveIdRiotByAccount(String account)
      throws CharacterNotAllowedException, AccountNotFoundDbException {
    String sql = "SELECT id FROM \"Account\" WHERE LOWER(name) =?;";
    Object[] params = {account.toLowerCase(Locale.ROOT)};
    if (isAlpha(account)) {
      try {
        return jdbcTemplate.queryForObject(sql, params, String.class);
      } catch (EmptyResultDataAccessException e) {
        throw new AccountNotFoundDbException(account);
      }
    } else {
      throw new CharacterNotAllowedException(account);
    }
  }

  /** Retrieve riot puuid by account.*/
  public String retrievePuuidRiotByAccount(String account)
      throws CharacterNotAllowedException, AccountNotFoundDbException {
    String sql = "SELECT puuid FROM \"Account\" WHERE LOWER(name) =?;";
    Object[] params = {account.toLowerCase(Locale.ROOT)};
    if (isAlpha(account)) {
      try {
        return jdbcTemplate.queryForObject(sql, params, String.class);
      } catch (EmptyResultDataAccessException e) {
        throw new AccountNotFoundDbException(account);
      }
    } else {
      throw new CharacterNotAllowedException(account);
    }
  }
}