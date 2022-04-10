package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Services.LeagueService;
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

    @GetMapping(value = "/account/division-history/{account}")
    public ResponseEntity<Object> divisionHistory(@PathVariable String account) {

        try {
            return new ResponseEntity<>(leagueService.divisionHistory(account), HttpStatus.OK);
        } catch (CharacterNotAllowedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AccountNotFoundException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/account/division-comparison/{account}")
    public ResponseEntity<Object> divisionComparison(@PathVariable String account){
        return new ResponseEntity<>(leagueService.divisionComparison(account), HttpStatus.OK);
    }
}