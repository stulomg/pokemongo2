package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class AccountForClashData {

    private String teamId;

    public AccountForClashData(String teamId) {
        this.teamId = teamId;
    }

    public AccountForClashData() {
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
