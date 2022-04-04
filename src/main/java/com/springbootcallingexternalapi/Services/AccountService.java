package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.*;
import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class AccountService {
    Logger logger = LoggerFactory.getLogger(RiotRequestorService.class);

    @Autowired
    AccountRepository accountRepository;

    public void deleteAccount(String owner, String account) throws AccountOrOwnerNotFoundException, CharacterNotAllowedException {
        accountRepository.deleteAccount(owner.toLowerCase(Locale.ROOT), account.toLowerCase(Locale.ROOT));
    }

    public List<AccountModel> retrieveAccountByOwner(String owner) throws CharacterNotAllowedException, OwnerNotFoundException {
        return accountRepository.retrieveAccountByOwner(owner.toLowerCase(Locale.ROOT));
    }

    public void accountUpdate(AccountModel model) throws AccountDataUpdateException {
        accountRepository.accountUpdate(model);
    }

    public List<AccountModel> retrieveAccountByName(String name) throws CharacterNotAllowedException, NameNotFoundException {
        return accountRepository.retrieveAccountByName(name.toLowerCase(Locale.ROOT));
    }
}

