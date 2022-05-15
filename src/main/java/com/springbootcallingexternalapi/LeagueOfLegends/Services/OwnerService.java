package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;

@Service
public class OwnerService {
    @Autowired
    OwnerRepository ownerRepository;

    public void insertOwner (OwnerModel ownerModel)  {
        ownerRepository.insertOwner(ownerModel);

    }

    public Long retrieveOwnerIdByOwnerName(String owner) throws  CharacterNotAllowedException, OwnerNotFoundException {
        try {
            return ownerRepository.retrieveOwnerIdByOwnerName(owner);
        } catch (EmptyResultDataAccessException e) {
            throw new OwnerNotFoundException(owner);
        }catch (CharacterNotAllowedException e2) {
            throw new CharacterNotAllowedException(owner);
        }
    }
}
