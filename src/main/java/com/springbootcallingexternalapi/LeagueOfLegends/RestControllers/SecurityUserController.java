package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityJwtDtoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityLoginUserModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityNewUserModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.Services.SecurityUserService;
import com.springbootcallingexternalapi.LeagueOfLegends.Util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            return new ResponseEntity<>(new Message("Incomplete information, information need to be added in the following order: name, userName, email, password"), HttpStatus.BAD_REQUEST);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new Message("Password is required"), HttpStatus.BAD_REQUEST);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(new Message("The username or email is already registered"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    @Cacheable(value="logins", key = "#securityLoginUserModel.userName")
    public ResponseEntity<SecurityJwtDtoModel> login(@Valid @RequestBody SecurityLoginUserModel securityLoginUserModel) {
        return new ResponseEntity<>(securityUserService.login(securityLoginUserModel), HttpStatus.OK);
    }
}