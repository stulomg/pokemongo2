package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.MasteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**This class host the functions that bring you information for Champion Masteries. */
@Service
public class MasteryService {

  @Autowired
  MasteryRepository masteryRepository;
  @Autowired
  AccountRepository accountRepository;

  public Object accountMasteryHistory(String account)
      throws CharacterNotAllowedException, AccountNotFoundException {
    Integer accountId = Math.toIntExact(accountRepository.retrieveAccountIdByAccountName(account));
    return masteryRepository.AccountMasteryHistory(account, accountId);
  }
}