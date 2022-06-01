package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedExceptionOwner;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.LeagueDataNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * League class controller.
 */
@RestController
public class LeagueRestController {

  @Autowired
  LeagueService leagueService;

  /**
   * Endpoint to search the history of divisions according to the account in the application.
   */
  @GetMapping(value = "/account/division-history/{account}")
  public ResponseEntity<Object> divisionHistory(@PathVariable String account) {

    try {
      return new ResponseEntity<>(leagueService.divisionHistory(account), HttpStatus.OK);
    } catch (CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (LeagueDataNotFoundException | AccountNotFoundException e1) {
      return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Endpoint to find the maximum division between two owners in the application.
   */
  @GetMapping(value = "/account/max-division/{owner}/{owner2}")
  public ResponseEntity<Object> divisionComparison(@PathVariable String owner,
      @PathVariable String owner2) {
    try {
      return new ResponseEntity<>(leagueService.getMaxDivision(owner, owner2), HttpStatus.OK);
    } catch (CharacterNotAllowedExceptionOwner | CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (OwnerNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}