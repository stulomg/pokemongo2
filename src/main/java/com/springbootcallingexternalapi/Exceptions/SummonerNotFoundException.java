package com.springbootcallingexternalapi.Exceptions;

import com.springbootcallingexternalapi.Models.LeagueInfoModel;


public class SummonerNotFoundException extends Exception {
    public SummonerNotFoundException(LeagueInfoModel leagueInfoModel) {
        super ("RIOT NO HA ENCONTRADO EL SUMMONER: "+ leagueInfoModel.getSummonerName() + " POR FAVOR RECTIFICAR");
    }
}
