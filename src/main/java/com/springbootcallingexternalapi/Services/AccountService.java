package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    Logger logger = LoggerFactory.getLogger(RiotRequestorService.class);

    @Autowired
    AccountRepository accountRepository;

    public void deleteAccount(String owner, String nombre) throws AccountOrOwnerNotFoundException {
        accountRepository.deleteAccount(owner, nombre);
    }

    public List<AccountModel> retrieveAccountByOwner (String owner){
        return accountRepository.retrieveAccountByOwner(owner);
    }

    public void accountUpdate (AccountModel model){

        accountRepository.accountUpdate(model);
    }
}
