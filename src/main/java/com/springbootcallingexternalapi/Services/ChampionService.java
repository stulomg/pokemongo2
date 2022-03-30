package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Repositories.ChampionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChampionService {

    @Autowired
    ChampionRepository championRepository;

    public String retrieveChampionNameByChampionId (Long championId){
        return championRepository.retrieveChampionByChampionId(championId);
    }
}
