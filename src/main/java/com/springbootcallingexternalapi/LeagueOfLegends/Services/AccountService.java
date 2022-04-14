package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public void deleteAccount(String owner, String account) throws AccountOrOwnerNotFoundException, CharacterNotAllowedException {
        accountRepository.deleteAccount(owner, account);
    }

    public List<AccountModel> retrieveAccountByOwner(String owner) throws CharacterNotAllowedException, OwnerNotFoundException {
        return accountRepository.retrieveAccountByOwner(owner.toLowerCase(Locale.ROOT));
    }

    public void accountUpdate(AccountModel model) throws CharacterNotAllowedException, AccountNotFoundException {
        accountRepository.accountUpdate(model);
    }

    public List<AccountModel> retrieveAccountByName(String name) throws CharacterNotAllowedException, AccountNotFoundException {
        return accountRepository.retrieveAccountByAccountName(name.toLowerCase(Locale.ROOT));
    }
}

