package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Exceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.QueueNotFoundException;
import com.springbootcallingexternalapi.Exceptions.SummonerIdNotFoundException;
import com.springbootcallingexternalapi.Exceptions.*;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.Services.ChampionService;
import com.springbootcallingexternalapi.Services.RiotRequestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class RiotRestController {

    @Autowired
    RiotRequestorService riotRequestorService;
    @Autowired
    ChampionService championService;

    @RequestMapping(value = "/call-riot/{account}/{owner}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> callRiot(@PathVariable String account, @PathVariable String owner){
        try {
            AccountBaseModel acc = riotRequestorService.getAccountAndAssignToOwner(account, owner);
            return new ResponseEntity<>(acc, HttpStatus.OK);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccountDataException | OwnerNotAllowed e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/call-riot/league/soloq/{account}")
    public ResponseEntity<Object> getSoloqLeague(@PathVariable String account){
        try {
            LeagueInfoModel response = riotRequestorService.getLeague(account);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AccountNotFoundException | QueueNotFoundException |SummonerIdNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccountDataException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (HttpClientErrorException.NotFound e2){
            return new ResponseEntity<>(e2.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "call-riot/mastery/{account}/{championName}")
    public ResponseEntity<Object> getMastery(@PathVariable String account , @PathVariable String championName){

        try{
            MasteryHistoryInfoModel response = riotRequestorService.getMastery(account , championName);
            return new ResponseEntity<>(response, HttpStatus.OK);
            }catch (ChampionNotFoundException | ChampionMasteryNotFoundException | AccountNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
            } catch (CharacterNotAllowedException e1) {
            return new ResponseEntity<>(e1.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
