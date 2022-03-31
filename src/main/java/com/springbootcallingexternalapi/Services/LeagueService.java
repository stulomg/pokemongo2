package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Repositories.LeagueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {
    Logger logger = LoggerFactory.getLogger(RiotRequestorService.class);
    @Autowired
    LeagueRepository leagueRepository;


    public Object divisionHistory(String summonerName) {
        return leagueRepository.divisionHistory(summonerName);
    }
}


