package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MasteryHistoryInfoModel;

public class AccountDataException extends Exception{
    public AccountDataException (AccountBaseModel account){
        super ("The data entered for the account "+ account.getName() +" are not valid, please rectify");
    }
    public AccountDataException(MasteryHistoryInfoModel account) {
        super("The data entered for the account " + account.getAccount() + " are not valid, please rectify");
    }
    public AccountDataException(LeagueInfoModel account) {
        super ("The data entered for the account "+ account.getSummonerName() +" are not valid, please rectify");
    }
}