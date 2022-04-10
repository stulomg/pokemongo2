package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Services.MostPopularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MostPopularRestController {
    @Autowired
    MostPopularService mostPopularService;

    @GetMapping(value = "/leaguedata/mostpopular")
    public ResponseEntity<Object> getMostPopular(){
        return new ResponseEntity<Object>("ayuda",HttpStatus.OK);


    }

}
