package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.Exceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.Repositories.ChampionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class ChampionService {

    @Autowired
    ChampionRepository championRepository;

    public Long retrieveChampionIdByChampionName(String championName) throws ChampionNotFoundException, ChampionMasteryNotFoundException{
        try {
            return championRepository.retrieveChampionIdByChampionName(championName);
        }catch (EmptyResultDataAccessException e) {
            throw new ChampionNotFoundException(championName);
        }catch (HttpClientErrorException e1){
            throw  new ChampionMasteryNotFoundException(championName);
        }
    }
}
