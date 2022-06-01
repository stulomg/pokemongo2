package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** Account class controller. */
@RestController
public class AccountRestController {

  @Autowired
  AccountService accountService;

  /** Endpoint to delete an account from the app. */
  @DeleteMapping(value = "/account/delete/{account}")
  public ResponseEntity<Object> deleteAccount(@PathVariable String account) {
    try {
      accountService.deleteAccount(account);
      return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
    } catch (AccountOrOwnerNotFoundException
        | AccountNotFoundException | OwnerNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /** Endpoint to search for an account according to its owner in the application. */
  @GetMapping(value = "/account/find-by-owner/{owner}")
  public ResponseEntity<Object> retrieveAccountByOwner(@PathVariable String owner) {
    try {
      return new ResponseEntity<>(accountService.retrieveAccountByOwner(owner), HttpStatus.OK);
    } catch (CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (OwnerNotFoundException e1) {
      return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  /** Endpoint to update the information of an account in the application. */
  @PutMapping(value = "/account/update")
  public ResponseEntity<Object> accountUpdate(@RequestBody AccountModel model) {
    try {
      accountService.accountUpdate(model);
    } catch (CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (AccountNotFoundException | OwnerNotFoundException e1) {
      return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
  }

  /** Endpoint to search for an account by name in the app. */
  @GetMapping(value = "/account/find-by-name/{account}")
  public ResponseEntity<Object> retrieveAccountByName(@PathVariable String account) {
    try {
      return new ResponseEntity<>(accountService.retrieveAccountByName(account), HttpStatus.OK);
    } catch (CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (AccountNotFoundException e1) {
      return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}

