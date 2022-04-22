package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class TeamAccountsForClashData {

    private String summonerId;

    private String position;

    public TeamAccountsForClashData(String summonerId, String position) {
        this.summonerId = summonerId;
        this.position = position;
    }

    public TeamAccountsForClashData() {
    }

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
