package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.ChampionsExceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.Exceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.QueueNotFoundException;
import com.springbootcallingexternalapi.Exceptions.SummonerNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.Services.ChampionService;
import com.springbootcallingexternalapi.Services.RiotRequestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        } catch (AccountDataException | OwnerNotAllowedException | CharacterNotAllowedException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/call-riot/league/soloq/{account}")
    public ResponseEntity<Object> getSoloqLeague(@PathVariable String account) throws SummonerNotFoundException, CharacterNotAllowedException {
        try {
            LeagueInfoModel response = riotRequestorService.getLeague(account);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AccountNotFoundException | QueueNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccountDataException | HttpClientErrorException.NotFound e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/call-riot/mastery/{account}/{championName}")
    public ResponseEntity<Object> getMastery(@PathVariable String account, @PathVariable String championName) {

        try {
            MasteryHistoryInfoModel response = riotRequestorService.getMastery(account, championName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ChampionNotFoundException | ChampionMasteryNotFoundException | AccountNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CharacterNotAllowedException | AccountDataException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
