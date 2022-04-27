package com.springbootcallingexternalapi.LeagueOfLegends.Security.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedExceptionOwner;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeagueRestController {
    @Autowired
    LeagueService leagueService;

    @GetMapping(value = "/account/division-history/{account}/{owner}")
    public ResponseEntity<Object> divisionHistory(@PathVariable String account) {

        try {
            return new ResponseEntity<>(leagueService.divisionHistory(account), HttpStatus.OK);
        } catch (CharacterNotAllowedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AccountNotFoundException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/account/max-division/{owner}/{owner2}")
    public ResponseEntity<Object> divisionComparison(@PathVariable String owner, @PathVariable String owner2) {
        try {
            return new ResponseEntity<>(leagueService.getMaxDivision(owner,owner2), HttpStatus.OK);
        } catch (OwnerNotFoundException | CharacterNotAllowedExceptionOwner e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}