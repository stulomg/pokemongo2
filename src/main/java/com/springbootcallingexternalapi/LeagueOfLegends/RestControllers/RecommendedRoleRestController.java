package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedRoleDataModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.RecommendedRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendedRoleRestController {
    @Autowired
    RecommendedRoleService recommendedRoleService;

    @GetMapping(value = "/loldata/clash/recommendedRole")
    public ResponseEntity<Object> getMostPopular(@RequestBody RecommendedRoleDataModel data) {
        try {
            return new ResponseEntity<>(recommendedRoleService.recommendedRoleRepository(data), HttpStatus.OK);
        } catch (NoDataException | CharacterNotAllowedException | AccountNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
