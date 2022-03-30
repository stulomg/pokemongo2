package com.springbootcallingexternalapi.Exceptions;

import com.springbootcallingexternalapi.Models.LeagueInfoModel;


public class SummonerIdNotFoundException extends Exception {
    public SummonerIdNotFoundException(LeagueInfoModel account) {
        super ("RIOT NO HA ENCONTRADO EL SUMMONER: "+ account.getSummonerName() + " POR FAVOR RECTIFICAR");
    }
}
