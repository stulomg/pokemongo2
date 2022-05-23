package com.springbootcallingexternalapi.LeagueOfLegends.Repositories.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    OwnerRepository ownerRepository;

    public void deleteAccount(String account) throws AccountOrOwnerNotFoundException, CharacterNotAllowedException, OwnerNotFoundException, AccountNotFoundException {
        Integer ownerID = Math.toIntExact(ownerRepository.retrieveOwnerIdByAccount(account.toLowerCase(Locale.ROOT)));
        accountRepository.deleteAccount(account,ownerID );
    }

    public List<AccountModel> retrieveAccountByOwner(String owner) throws CharacterNotAllowedException, OwnerNotFoundException {
        Integer ownerID = Math.toIntExact(ownerRepository.retrieveOwnerIdByOwnerName(owner.toLowerCase(Locale.ROOT)));
        return accountRepository.retrieveAccountByOwner(owner.toLowerCase(Locale.ROOT),ownerID);
    }

    public void accountUpdate(AccountModel model) throws CharacterNotAllowedException, AccountNotFoundException, OwnerNotFoundException {
        Integer ownerID = Math.toIntExact(ownerRepository.retrieveOwnerIdByOwnerName(String.valueOf(model.getOwnerName())));
        accountRepository.accountUpdate(model,ownerID);
    }

    public List<AccountModel> retrieveAccountByName(String name) throws CharacterNotAllowedException, AccountNotFoundException {
        return accountRepository.retrieveAccountByAccountName(name.toLowerCase(Locale.ROOT));
    }
}