package com.springbootcallingexternalapi.Models;

public class MostPopularModel {
    private String account;

    private String championName;

    private int championPoints;

    public MostPopularModel(String account, String championName, int championPoints) {
        this.account = account;
        this.championName = championName;
        this.championPoints = championPoints;
    }

    public MostPopularModel() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public int getChampionPoints() {
        return championPoints;
    }

    public void setChampionPoints(int championPoints) {
        this.championPoints = championPoints;
    }
}
