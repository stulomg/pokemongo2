package com.springbootcallingexternalapi.Services;


import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Repositories.MasteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasteryService {

    @Autowired
    MasteryRepository masteryRepository;

    public Object AccountMasteryHistory (String account) throws CharacterNotAllowedException, AccountNotFoundException {
       return masteryRepository.AccountMasteryHistory(account);
    }
}
