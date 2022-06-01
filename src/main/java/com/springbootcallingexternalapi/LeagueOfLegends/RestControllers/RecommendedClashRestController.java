package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashDataModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.RecommendedClashService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clash class controller.
 */
@RestController
public class RecommendedClashRestController {

  @Autowired
  RecommendedClashService recommendedClashService;

  /**
   * Endpoint to recommend position and champion to choose from 5 accounts previously registered in
   * the application.
   */
  @GetMapping(value = "/loldata/clash/recommended")
  public ResponseEntity<Object> getRecommendedRole(
      @RequestBody List<RecommendedClashDataModel> data) {
    try {
      return new ResponseEntity<>(recommendedClashService.recommendedClashService(data),
          HttpStatus.OK);
    } catch (CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (NoDataException | AccountNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}