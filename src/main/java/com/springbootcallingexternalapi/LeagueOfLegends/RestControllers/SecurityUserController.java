package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Security.InformationMissingException;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.SecurityUserService;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityJwtDtoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityLoginUserModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Util.Message;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityNewUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class SecurityUserController {

    @Autowired
    SecurityUserService securityUserService;

    @PostMapping("/newUser")
    public ResponseEntity<?> newUser(@Valid @RequestBody SecurityNewUserModel newUser) {
        try {
            securityUserService.newUser(newUser);
            return new ResponseEntity(new Message("New registered user"), HttpStatus.CREATED);
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<SecurityJwtDtoModel> login(@Valid @RequestBody SecurityLoginUserModel securityLoginUserModel, BindingResult bindingResult) {

        return new ResponseEntity<>(securityUserService.login(securityLoginUserModel), HttpStatus.OK);

    }
}
