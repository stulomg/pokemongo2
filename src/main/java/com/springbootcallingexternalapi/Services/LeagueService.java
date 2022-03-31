package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Repositories.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueService {

    @Autowired
    LeagueRepository leagueRepository;

    public Object divisionHistory(String summonerName) {
        return leagueRepository.divisionHistory(summonerName);
    }
}


