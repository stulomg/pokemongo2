package com.springbootcallingexternalapi.LeagueOfLegends.Security.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.MostPopularService;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.RecommendedRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class RecommendedRoleRestController {
    @Autowired
    RecommendedRoleService recommendedRoleService;
    @GetMapping(value = "/loldata/clash/recommendedRole/{account1}/{account2}/{account3}/{account4}/{account5}")
    public ResponseEntity<Object> getMostPopular(@PathVariable String account1,@PathVariable String account2,@PathVariable String account3,@PathVariable String account4,@PathVariable String account5){
        try {
            return new ResponseEntity<>(recommendedRoleService.recommendedRoleRepository(account1.toLowerCase(Locale.ROOT),account2.toLowerCase(Locale.ROOT),account3.toLowerCase(Locale.ROOT),account4.toLowerCase(Locale.ROOT),account5.toLowerCase(Locale.ROOT)), HttpStatus.OK);
        } catch (NoDataException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
