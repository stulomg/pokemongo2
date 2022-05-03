package com.springbootcallingexternalapi.LeagueOfLegends.Services;


import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.MasteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasteryService {

    @Autowired
    MasteryRepository masteryRepository;

    public Object AccountMasteryHistory(String account) throws CharacterNotAllowedException, AccountNotFoundException {
        return masteryRepository.AccountMasteryHistory(account);
    }
}
