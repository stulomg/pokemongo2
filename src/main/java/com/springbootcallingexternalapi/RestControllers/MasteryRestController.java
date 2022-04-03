package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Exceptions.*;
import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.Services.RiotRequestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasteryRestController {
    @Autowired
    RiotRequestorService riotRequestorService;
    @PutMapping(value = "/mastery/add/{account}/{championName}")
    public ResponseEntity<Object> newMastery(@PathVariable String account,@PathVariable String championName) throws AccountNotFoundException {
        try {
            MasteryHistoryInfoModel response = riotRequestorService.getMastery(account,championName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ChampionNotFoundException | CharacterNotAllowedException | ChampionMasteryNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("New champion added successfully", HttpStatus.OK);
    }
}
