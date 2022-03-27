package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Exceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController  {
    @Autowired
    AccountService accountService;

    @DeleteMapping(value = "/account/delete/{owner}/{nombre}")
    public ResponseEntity<Object> deleteAccount (@PathVariable String owner, @PathVariable String nombre){
        try{
        accountService.deleteAccount(owner, nombre);
        return new ResponseEntity<>("Delete succesfully", HttpStatus.OK);
        }catch (AccountOrOwnerNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
