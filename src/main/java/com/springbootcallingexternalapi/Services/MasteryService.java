package com.springbootcallingexternalapi.Services;


import com.springbootcallingexternalapi.Exceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.Repositories.MasteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasteryService {

    @Autowired
    MasteryRepository masteryRepository;

    public Object AccountMasteryHistory (String account) throws CharacterNotAllowedException, AccountNotFoundException {
       return masteryRepository.AccountMasteryHistory(account);
    }
}
