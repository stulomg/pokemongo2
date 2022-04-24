package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.QueueNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameInfoBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.ChampionService;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.RiotRequestorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Calendar;

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

    @GetMapping(value = "/call-riot/league/soloq/{account}/{owner}")
    public ResponseEntity<Object> getSoloqLeague(@PathVariable String account, @PathVariable String owner) throws CharacterNotAllowedException {
        try {
            LeagueInfoModel response = riotRequestorService.getSoloqLeague(account, owner);
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
    public ResponseEntity<Object> getLiveMatch(@PathVariable String account) {
        try {
            CurrentGameInfoBaseModel response = riotRequestorService.getLiveMatch(account);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CharacterNotAllowedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AccountNotFoundException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/call-riot/server/status")

    public ResponseEntity<Object> serverStatus() {
        Object response = riotRequestorService.serverStatus();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/call-riot/matches/{account}")

    public ResponseEntity<Object> getMatches(@PathVariable String account) throws AccountNotFoundException {
        Object response = riotRequestorService.getListMatches(account);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/call-riot/clash/{account}")

    public ResponseEntity<Object> getAccountForClash(@PathVariable String account) {
        try {
            Object response = riotRequestorService.getAccountForClash(account);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}