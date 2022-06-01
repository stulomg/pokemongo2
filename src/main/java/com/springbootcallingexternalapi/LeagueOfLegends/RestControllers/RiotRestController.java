package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundDBException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.PlayerNotInGameException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Position.PositionNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.QueueNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameInfoBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameInfoRuneModel;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

/** Riot class controller. */
@RestController
public class RiotRestController {

  Logger logger = LoggerFactory.getLogger(RiotRestController.class);

  @Autowired
  RiotRequestorService riotRequestorService;
  @Autowired
  ChampionService championService;

  /** Endpoint to register a new account according to its owner in the application. */
  @RequestMapping(value = "/call-riot/{account}/{owner}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> callRiot(@PathVariable String account, @PathVariable String owner) {
    try {
      AccountBaseModel acc = riotRequestorService.getAccountAndAssignToOwner(account, owner);
      return new ResponseEntity<>(acc, HttpStatus.OK);
    } catch (AccountNotFoundException | OwnerNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (AccountDataException | CharacterNotAllowedException e1) {
      return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /** Endpoint to record the information of the current league of an account at the moment
   *  in the application. */
  @GetMapping(value = "/call-riot/league/soloq/{account}")
  public ResponseEntity<Object> getSoloqLeague(@PathVariable String account) {
    try {
      LeagueInfoModel response = riotRequestorService.getSoloqLeague(account);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (AccountNotFoundException | QueueNotFoundException | AccountNotFoundDBException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (AccountDataException | HttpClientErrorException.NotFound
        | CharacterNotAllowedException e1) {
      return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /** Endpoint to register the mastery of a champion according to the account in the application. */
  @GetMapping(value = "/call-riot/mastery/{account}/{championName}")
  public ResponseEntity<Object> getMastery(@PathVariable String account,
      @PathVariable String championName) {

    try {
      MasteryHistoryInfoModel response = riotRequestorService.getMastery(account, championName);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (ChampionNotFoundException | ChampionMasteryNotFoundException
        | AccountNotFoundException | AccountNotFoundDBException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (CharacterNotAllowedException | AccountDataException e1) {
      logger.info(e1.getMessage());
      return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /** Endpoint to display the information of a game in progress of an account
   * previously registered in the application. */
  @GetMapping(value = "/call-riot/live/match/{account}")
  public ResponseEntity<Object> getLiveMatch(@PathVariable String account) {
    try {
      CurrentGameInfoBaseModel response = riotRequestorService.getLiveMatch(account);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (CharacterNotAllowedException | PlayerNotInGameException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (AccountNotFoundException | AccountNotFoundDBException e1) {
      return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  /** Endpoint to record the information about the runes of the players of a game
   * in progress in the application. */
  @GetMapping(value = "/call-riot/live/match/runes/{account}")
  public ResponseEntity<Object> getRune(@PathVariable String account) {
    try {
      CurrentGameInfoRuneModel response = riotRequestorService.getCurrentGameRunes(account);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (CharacterNotAllowedException | JsonProcessingException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (AccountNotFoundException | AccountNotFoundDBException e1) {
      return new ResponseEntity<>(e1.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  /** Endpoint to record the information about the status of the league of legends servers
   * in the application. */
  @GetMapping(value = "/call-riot/server/status")
  public ResponseEntity<Object> serverStatus() {

    Object response = riotRequestorService.serverStatus();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /** Endpoint to record the information of the last 5 games in soloQ of an account
   *  in the application. */
  @GetMapping(value = "/call-riot/matches/{account}")
  public ResponseEntity<Object> getMatches(@PathVariable String account) {
    Object response = null;
    try {
      response = riotRequestorService.getListMatches(account);
    } catch (AccountNotFoundException | PositionNotFoundException
        | ChampionNotFoundException | AccountDataException
        | ChampionMasteryNotFoundException | AccountNotFoundDBException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /** Endpoint to see if an account is in a Clash team. */
  @GetMapping(value = "/call-riot/clash/{account}")
  public ResponseEntity<Object> getAccountForClash(@PathVariable String account) {
    try {
      Object response = riotRequestorService.getAccountsForClash(account);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (AccountNotFoundException | ChampionNotFoundException
        | AccountDataException | ChampionMasteryNotFoundException | AccountNotFoundDBException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /** Endpoint that reviews the relationship according to games played between two accounts. */
  @GetMapping(value = "/relationship/{account1}/{account2}")
  public ResponseEntity<Object> playersRelationship(@PathVariable String account1,
      @PathVariable String account2) {
    Object response = null;
    try {
      response = riotRequestorService.playersRelationship(account1, account2);
    } catch (CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (AccountNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
