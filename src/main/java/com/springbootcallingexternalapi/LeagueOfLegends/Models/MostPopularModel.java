package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import java.sql.Timestamp;

public class MostPopularModel {
    private String Account;
    private String championName;
    private String date;

    public MostPopularModel() {
    }

    public MostPopularModel(String account, String championName, String date) {
        Account = account;
        this.championName = championName;
        this.date = date;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
