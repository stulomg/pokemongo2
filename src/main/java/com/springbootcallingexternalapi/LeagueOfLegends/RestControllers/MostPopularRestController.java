package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.MostPopularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Most Popular class controller.
 */
@RestController
public class MostPopularRestController {

  @Autowired
  MostPopularService mostPopularService;

  /**
   * Endpoint to search for the most popular account (the most consulted account in the last month)
   * in the application.
   */
  @GetMapping(value = "/loldata/mostpopular")
  public ResponseEntity<Object> getMostPopular() {
    try {
      return new ResponseEntity<>(mostPopularService.mostpopularService(), HttpStatus.OK);
    } catch (NoDataException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}