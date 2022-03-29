package com.springbootcallingexternalapi.Exceptions;

import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;

public class PlayerIDNotFoundException extends Exception {
    public PlayerIDNotFoundException(LeagueInfoModel account) {
        super ("RIOT NO HA ENCONTRADO EL ID: "+ account.getId() + "POR FAVOR RECTIFICAR");
    }
}
