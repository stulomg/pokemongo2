package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.DBException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.MostPopularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MostPopularRestController {
    @Autowired
    MostPopularService mostPopularService;

    @GetMapping(value = "/loldata/mostpopular")
    public ResponseEntity<Object> getMostPopular(){
        try {
            return new ResponseEntity<>(mostPopularService.mostpopularRepository(),HttpStatus.OK);
        } catch (NoDataException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
