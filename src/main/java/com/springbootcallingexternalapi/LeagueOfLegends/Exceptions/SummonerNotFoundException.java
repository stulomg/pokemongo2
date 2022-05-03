package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;


public class SummonerNotFoundException extends Exception {
    public SummonerNotFoundException(LeagueInfoModel leagueInfoModel) {
        super("RIOT NO HA ENCONTRADO EL SUMMONER: " + leagueInfoModel.getSummonerName() + " POR FAVOR RECTIFICAR");
    }
}
