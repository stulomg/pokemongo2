package com.springbootcallingexternalapi.Exceptions;

import com.springbootcallingexternalapi.Models.LeagueInfoModel;


public class PlayerIDNotFoundException extends Exception {
    public PlayerIDNotFoundException(LeagueInfoModel account) {
        super ("RIOT NO HA ENCONTRADO EL ID: "+ account.getSummonerName() + "POR FAVOR RECTIFICAR");
    }
}
