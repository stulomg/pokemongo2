package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;

/**
 * Exception for when Riot not found summoner data.
 */
public class SummonerNotFoundException extends Exception {

  public SummonerNotFoundException(LeagueInfoModel leagueInfoModel) {
    super(
        "Riot hasn't found the summoner: " + leagueInfoModel.getSummonerName() + " please rectify");
  }
}