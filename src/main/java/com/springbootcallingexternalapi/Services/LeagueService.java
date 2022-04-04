package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.*;
import com.springbootcallingexternalapi.Repositories.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueService {

    @Autowired
    LeagueRepository leagueRepository;

    public Object divisionHistory(String summonerName) throws SummoneCharacterNotAllowedException, SummonernameNotFoundException {
            return leagueRepository.divisionHistory(summonerName);
        }
    }


