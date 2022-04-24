package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class RecommendedRoleModel {
    private String summonerName ;
    private String recommendPosition ;
    private Integer gamesPlayed ;

    public RecommendedRoleModel() {
    }

    public RecommendedRoleModel(String summonerName, String recommendPosition, Integer gamesPlayed) {
        this.summonerName = summonerName;
        this.recommendPosition = recommendPosition;
        this.gamesPlayed = gamesPlayed;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getRecommendPosition() {
        return recommendPosition;
    }

    public void setRecommendPosition(String recommendPosition) {
        this.recommendPosition = recommendPosition;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}
