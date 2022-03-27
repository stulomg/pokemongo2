package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    Logger logger = LoggerFactory.getLogger(RiotRequestorService.class);

    @Autowired
    AccountRepository accountRepository;
    public void deleteAccount (String owner, String nombre){
        accountRepository.deleteAccount(owner, nombre);
    }
}
