package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerAlreadyExists;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Owner class controller.
 */
@RestController
public class OwnerRestController {

  @Autowired
  OwnerService ownerService;

  /**
   * Endpoint to register a new owner in the application.
   */
  @GetMapping(value = "/account/new-owner")
  public ResponseEntity<Object> newOwner(@RequestBody OwnerModel ownerModel) {
    try {
      ownerService.insertOwner(ownerModel);
    } catch (OwnerAlreadyExists | CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>("Owner has been added correctly", HttpStatus.OK);
  }
}