package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Services.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeagueRestControler {
    @Autowired
    LeagueService leagueService;

    @GetMapping(value = "/account/division-history/{summonerName}")
    public ResponseEntity<Object> divisionHistory(@PathVariable String summonerName) {

            return new ResponseEntity<>(leagueService.divisionHistory(summonerName), HttpStatus.OK);
    }
}

