package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerAllreadyRegisterException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwnerRestController {
    @Autowired
    OwnerService ownerService;

    @PostMapping(value = "/account/create")

    public ResponseEntity<Object> create(@RequestBody OwnerModel newOwner) {
        try {
            ownerService.ownerRepository(newOwner);
        } catch (OwnerAllreadyRegisterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Owner created", HttpStatus.OK);
    }
}

