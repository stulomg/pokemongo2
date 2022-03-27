package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Services.RiotRequestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class RiotRestController {

    @Autowired
    RiotRequestorService riotRequestorService;

    @RequestMapping(value =  "/call-riot/{account}",
                    method = RequestMethod.GET)
    public ResponseEntity<Object> callRiot(@PathVariable String account) {
        try {
            AccountBaseModel acc = riotRequestorService.getAccountFromRiot(account);
            return new ResponseEntity<>(acc, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
