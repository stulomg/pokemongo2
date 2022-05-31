package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.*;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.QueryModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QueryRestController {
    @Autowired
    QueryService queryService;

    @PostMapping(value = "/loldata/newquery")
    public ResponseEntity<Object> newquery(@RequestBody QueryModel queryModel) {
        try {
            return new ResponseEntity<>(queryService.specificQuery(queryModel), HttpStatus.OK);
        } catch (NoDataException   e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (QuerySyntaxErrorException | QueryInvalidParameterException | QueryCriteriaExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/loldata/listquery")
    public ResponseEntity<Object> listquery()  {
        try {
            return new ResponseEntity<>(queryService.listQuery(), HttpStatus.OK);
        } catch (QueryNoDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/loldata/query/{criteria}")
    public ResponseEntity<Object> query(@PathVariable String criteria)  {
        try {
            return new ResponseEntity<>(queryService.selectQuery(criteria), HttpStatus.OK);
        }catch (QuerySyntaxErrorException  e ) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (NoDataException | QueryFilterNoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
