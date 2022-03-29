package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Exceptions.AccountDataUpdateException;
import com.springbootcallingexternalapi.Exceptions.AccountOrOwnerNotFoundException;
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

    @DeleteMapping(value = "/account/delete/{owner}/{nombre}")
    public ResponseEntity<Object> deleteAccount(@PathVariable String owner, @PathVariable String nombre) {
        try {
            accountService.deleteAccount(owner, nombre);
            return new ResponseEntity<>("Delete succesfully", HttpStatus.OK);
        } catch (AccountOrOwnerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/account/find-by-owner/{owner}")
    public ResponseEntity<Object> retrieveAccountByOwner(@PathVariable String owner) {
        return new ResponseEntity<>(  accountService.retrieveAccountByOwner(owner) , HttpStatus.OK);
    }

    @PutMapping(value = "/account/update")
    public ResponseEntity<Object> accountUpdate(@RequestBody AccountModel model) throws AccountDataUpdateException {
        accountService.accountUpdate(model);
        return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/account/find-by-name/{name}")
    public ResponseEntity<Object> retrieveAccountByName(@PathVariable String name) {
        return new ResponseEntity<>(  accountService.retrieveAccountByName(name) , HttpStatus.OK);
    }
}

