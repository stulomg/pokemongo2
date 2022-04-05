package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Exceptions.OwnerAllreadyRegisterException;
import com.springbootcallingexternalapi.Models.OwnerModel;
import com.springbootcallingexternalapi.Services.OwnerService;
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

