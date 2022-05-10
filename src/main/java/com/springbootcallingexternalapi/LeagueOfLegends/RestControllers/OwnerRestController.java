package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwnerRestController {
    @Autowired
    OwnerService ownerService;

    @GetMapping(value = "/account/new-owner")
    public ResponseEntity<Object> newOwner(@RequestBody OwnerModel ownerModel) {
        ownerService.insertOwner(ownerModel);
        return new ResponseEntity<>("Se agrego el owner correctamente", HttpStatus.OK);
    }
}
