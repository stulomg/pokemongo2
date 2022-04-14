package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.ChampionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class ChampionService {

    @Autowired
    ChampionRepository championRepository;

    public Long retrieveChampionIdByChampionName(String championName) throws ChampionNotFoundException, ChampionMasteryNotFoundException, CharacterNotAllowedException {
        try {
            return championRepository.retrieveChampionIdByChampionName(championName);
        }catch (EmptyResultDataAccessException e) {
            throw new ChampionNotFoundException(championName);
        }catch (HttpClientErrorException e1){
            throw  new ChampionMasteryNotFoundException(championName);
        }catch (CharacterNotAllowedException e2){
            throw new CharacterNotAllowedException(championName);
        }
    }
}
