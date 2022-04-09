package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Repositories.AccountRepository;
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

    public void accountUpdate(AccountModel model) throws AccountDataException {
        accountRepository.accountUpdate(model);
    }

    public List<AccountModel> retrieveAccountByName(String name) throws CharacterNotAllowedException, AccountNotFoundException {
        return accountRepository.retrieveAccountByAccountName(name.toLowerCase(Locale.ROOT));
    }
}

