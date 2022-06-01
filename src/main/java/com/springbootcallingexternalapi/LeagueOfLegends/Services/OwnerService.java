package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerAlreadyExists;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**This class host the functions that insert or retrieve you an Owner. */
@Service
public class OwnerService {

  @Autowired
  OwnerRepository ownerRepository;

  public void insertOwner(OwnerModel ownerModel)
      throws OwnerAlreadyExists, CharacterNotAllowedException {
    ownerRepository.insertOwner(ownerModel);
  }

  public Long retrieveOwnerIdByOwnerName(String owner)
      throws CharacterNotAllowedException, OwnerNotFoundException {
    return ownerRepository.retrieveOwnerIdByOwnerName(owner);
  }
}
