package com.springbootcallingexternalapi.Exceptions.AccountExceptions;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;

public class AccountDataException extends Exception{
    public AccountDataException (AccountBaseModel account){
        super ("LOS DATOS INGRESADOS PARA LA CUENTA "+ account.getName() +" NO SON VALIDOS, POR FAVOR RECTIFICAR");
    }

    public AccountDataException (MasteryHistoryInfoModel account){
        super ("LOS DATOS INGRESADOS PARA LA CUENTA "+ account.getAccount() +" NO SON VALIDOS, POR FAVOR RECTIFICAR");
    }

    public AccountDataException(LeagueInfoModel account) {
        super ("LOS DATOS INGRESADOS PARA LA CUENTA "+ account.getSummonerName() +" NO SON VALIDOS, POR FAVOR RECTIFICAR");
    }
}
