package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerAllreadyRegisterException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    public void ownerRepository(OwnerModel model) throws OwnerAllreadyRegisterException {
        ownerRepository.createOwner(model);
    }
}
