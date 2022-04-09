package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.ChampionsExceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.Exceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.QueueNotFoundException;
import com.springbootcallingexternalapi.Models.*;
import com.springbootcallingexternalapi.Exceptions.SummonerNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.Services.ChampionService;
import com.springbootcallingexternalapi.Services.RiotRequestorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class RiotRestController {
    Logger logger = LoggerFactory.getLogger(RiotRestController.class);

    @Autowired
    RiotRequestorService riotRequestorService;
    @Autowired
    ChampionService championService;

    @RequestMapping(value = "/call-riot/{account}/{owner}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> callRiot(@PathVariable String account, @PathVariable String owner) {
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
            LeagueInfoModel response = riotRequestorService.getSoloqLeague(account);
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
            logger.info(e1.getMessage());
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/call-riot/live/match/{account}")
    public ResponseEntity<Object> getLiveMatch(@PathVariable String account) throws AccountNotFoundException {
        CurrentGameInfoBaseModel response = riotRequestorService.getLiveMatch(account);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/call-riot/server/status")
    public ResponseEntity<Object> serverStatus (){
        Object response = riotRequestorService.serverStatus();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
