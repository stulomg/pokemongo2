package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.MasteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasteryRestController {

  @Autowired
  MasteryService masteryService;

  @GetMapping(value = "/account/masteryHistory/{account}")

  public ResponseEntity<Object> AccountMasteryHistory(@PathVariable String account) {
    try {
      return new ResponseEntity<>(masteryService.accountMasteryHistory(account), HttpStatus.OK);
    } catch (CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (AccountNotFoundException e1) {
      return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}