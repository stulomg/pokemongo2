package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.OwnerRepository;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is the class that host the CRUD services for Riot Games Accounts.
 */
@Service
public class AccountService {

  @Autowired
  AccountRepository accountRepository;
  @Autowired
  OwnerRepository ownerRepository;

  /**
   * This function delet an account from database.
   */
  public void deleteAccount(String account)
      throws AccountOrOwnerNotFoundException, CharacterNotAllowedException, OwnerNotFoundException,
      AccountNotFoundException {
    Integer ownerId = Math.toIntExact(
        accountRepository.retrieveOwnerIdByAccount(account.toLowerCase(Locale.ROOT)));
    accountRepository.deleteAccount(account, ownerId);
  }

  /**
   * This function retrieve an Account info bringing the Account Owner.
   */
  public List<AccountModel> retrieveAccountByOwner(String owner)
      throws CharacterNotAllowedException, OwnerNotFoundException {
    Integer ownerId = Math.toIntExact(
        ownerRepository.retrieveOwnerIdByOwnerName(owner.toLowerCase(Locale.ROOT)));
    return accountRepository.retrieveAccountByOwner(owner.toLowerCase(Locale.ROOT), ownerId);
  }

  /**
   * This function allows you to update an account info.
   */
  public void accountUpdate(AccountModel model)
      throws CharacterNotAllowedException, AccountNotFoundException, OwnerNotFoundException {
    Integer ownerId = Math.toIntExact(
        ownerRepository.retrieveOwnerIdByOwnerName(String.valueOf(model.getOwnerName())));
    accountRepository.accountUpdate(model, ownerId);
  }

  /**
   * This function retrieve an Account info bringing the Account Owner.
   */
  public List<AccountModel> retrieveAccountByName(String name)
      throws CharacterNotAllowedException, AccountNotFoundException {
    return accountRepository.retrieveAccountByAccountName(name.toLowerCase(Locale.ROOT));
  }
}