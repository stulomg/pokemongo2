package com.springbootcallingexternalapi.LeagueOfLegends.Repositories.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.MasteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasteryService {
    @Autowired
    MasteryRepository masteryRepository;
    @Autowired
    AccountRepository accountRepository;

    public Object AccountMasteryHistory(String account) throws CharacterNotAllowedException, AccountNotFoundException {
        Integer accountID = Math.toIntExact(accountRepository.retrieveAccountIdByAccountName(account));
        return masteryRepository.AccountMasteryHistory(account,accountID);
    }
}