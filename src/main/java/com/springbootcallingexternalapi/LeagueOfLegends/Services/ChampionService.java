package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.ChampionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class host the function to bring info about League of legends champions.
 */
@Service
public class ChampionService {

  @Autowired
  ChampionRepository championRepository;

  /**
   * This function allows to bring the championId giving a championName.
   */
  public Long retrieveChampionIdByChampionName(String championName)
      throws ChampionNotFoundException, CharacterNotAllowedException {

    if (championName.equals("monkeyking") || championName.equals("MonkeyKing")) {
      championName = "wukong";
      return championRepository.retrieveChampionIdByChampionName(championName);
    } else {
      return championRepository.retrieveChampionIdByChampionName(championName);
    }
  }
}