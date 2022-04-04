package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Exceptions.*;
import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.Services.RiotRequestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.springbootcallingexternalapi.Util.AlphaVerifier.isAlpha;

@RestController
public class MasteryRestController {
    @Autowired
    RiotRequestorService riotRequestorService;

}
