package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class CurrentGameRunesModel {

    private String summonerName;

    private Object perks;

    @Override
    public String toString() {
        return "CurrentGameParticipantModel{" +
                ", summonerName='" + summonerName + '\'' +
                ", perks=" + perks +
                '}';
    }

    public CurrentGameRunesModel(String summonerName, Object perks) {

        this.summonerName = summonerName;
        this.perks = perks;
    }

    public CurrentGameRunesModel() {
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public Object getPerks() {
        return perks;
    }

    public void setPerks(Object perks) {
        this.perks = perks;
    }
}
