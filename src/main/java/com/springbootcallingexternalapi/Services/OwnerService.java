package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.OwnerAllreadyRegisterException;
import com.springbootcallingexternalapi.Exceptions.OwnerWrongIdException;
import com.springbootcallingexternalapi.Models.OwnerModel;
import com.springbootcallingexternalapi.Repositories.OwnerRepository;
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
