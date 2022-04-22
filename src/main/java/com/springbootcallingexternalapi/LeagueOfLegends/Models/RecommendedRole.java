package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class RecommendedRole {
    private String summonerName ;
    private String teamPosition ;
    private Integer mrole ;

    public RecommendedRole() {
    }

    public RecommendedRole(String summonerName, String teamPosition, Integer mrole) {
        this.summonerName = summonerName;
        this.teamPosition = teamPosition;
        this.mrole = mrole;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getTeamPosition() {
        return teamPosition;
    }

    public void setTeamPosition(String teamPosition) {
        this.teamPosition = teamPosition;
    }

    public Integer getMrole() {
        return mrole;
    }

    public void setMrole(Integer mrole) {
        this.mrole = mrole;
    }
}
