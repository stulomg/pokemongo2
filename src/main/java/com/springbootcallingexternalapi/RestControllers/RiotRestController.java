package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Exceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.PlayerIDNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.Services.RiotRequestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class RiotRestController {

    @Autowired
    RiotRequestorService riotRequestorService;

    @RequestMapping(value =  "/call-riot/{owner}/{account}",
                    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> callRiot(@PathVariable String account, @PathVariable String owner) throws AccountDataException, AccountNotFoundException {
        try {
            AccountBaseModel acc = riotRequestorService.getAccountAndAssignToOwner(account,owner);
            return new ResponseEntity<>(acc, HttpStatus.OK);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccountDataException e1){
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/call-riot/league/{account}")
        public ResponseEntity<Object> getLeague (@PathVariable String account)throws PlayerIDNotFoundException{
            try {
                LeagueInfoModel response = riotRequestorService.getLeague(account);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (AccountNotFoundException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
            } catch (AccountDataException e1) {
                return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

