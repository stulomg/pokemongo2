package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountRestController {
    @Autowired
    AccountService accountService;

    @DeleteMapping(value = "/account/delete/{owner}/{account}")
    public ResponseEntity<Object> deleteAccount(@PathVariable String owner, @PathVariable String account) {
        try {
            accountService.deleteAccount(owner, account);
            return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
        } catch (AccountOrOwnerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CharacterNotAllowedException e2) {
            return new ResponseEntity<>(e2.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/account/find-by-owner/{owner}")
    public ResponseEntity<Object> retrieveAccountByOwner(@PathVariable String owner){
        try {
            return new ResponseEntity<>(  accountService.retrieveAccountByOwner(owner) , HttpStatus.OK);
        }catch (CharacterNotAllowedException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (OwnerNotFoundException e1){
            return new ResponseEntity<>(e1.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/account/update")
    public ResponseEntity<Object> accountUpdate(@RequestBody AccountModel model) {
        try {
            accountService.accountUpdate(model);
        } catch (AccountNotFoundException | CharacterNotAllowedException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
    }


    @GetMapping(value = "/account/find-by-name/{AccountName}")
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

