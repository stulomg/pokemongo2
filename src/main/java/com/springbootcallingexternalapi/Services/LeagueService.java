package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Repositories.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueService {

    @Autowired
    LeagueRepository leagueRepository;

    public Object divisionHistory(String account) throws CharacterNotAllowedException, AccountNotFoundException {
        return leagueRepository.divisionHistory(account);
    }
    public Object divisionComparison(String account){
        return leagueRepository.divisionComparison(account);
    }
}

