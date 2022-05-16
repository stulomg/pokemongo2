package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class RecommendedRoleModel {
    private Integer account ;
    private Integer recommendPosition ;
    private Integer gamesPlayed ;

    public RecommendedRoleModel() {
    }

    public RecommendedRoleModel(Integer account, Integer recommendPosition, Integer gamesPlayed) {
        this.account = account;
        this.recommendPosition = recommendPosition;
        this.gamesPlayed = gamesPlayed;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getRecommendPosition() {
        return recommendPosition;
    }

    public void setRecommendPosition(Integer recommendPosition) {
        this.recommendPosition = recommendPosition;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}
