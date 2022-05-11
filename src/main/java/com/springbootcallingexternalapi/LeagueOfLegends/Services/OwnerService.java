package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.ChampionsExceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.ChampionRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class OwnerService {
    @Autowired
    OwnerRepository ownerRepository;

    public void insertOwner (OwnerModel ownerModel)  {
        ownerRepository.insertOwner(ownerModel);

    }

    public Long retrieveChampionIdByChampionName(String owner) throws  CharacterNotAllowedException, OwnerNotFoundException {
        try {
            return ownerRepository.retrieveOwnerIdByOwnerName(owner);
        } catch (EmptyResultDataAccessException e) {
            throw new OwnerNotFoundException(owner);
        }catch (CharacterNotAllowedException e2) {
            throw new CharacterNotAllowedException(owner);
        }
    }
}
