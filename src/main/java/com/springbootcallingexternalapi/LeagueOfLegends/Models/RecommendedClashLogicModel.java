package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class RecommendedClashLogicModel {
    private Integer account;
    private Integer champion;
    private Integer gamesChampion;
    private Integer TotalGamesAccount;
    private Integer masteryChampion;
    private Integer TotalMasteryAccount;
    private Integer GamesWinChampion;

    public RecommendedClashLogicModel() {
    }

    public RecommendedClashLogicModel(Integer account, Integer champion, Integer gamesChampion, Integer totalGamesAccount, Integer masteryChampion, Integer totalMasteryAccount, Integer gamesWinChampion) {
        this.account = account;
        this.champion = champion;
        this.gamesChampion = gamesChampion;
        TotalGamesAccount = totalGamesAccount;
        this.masteryChampion = masteryChampion;
        TotalMasteryAccount = totalMasteryAccount;
        GamesWinChampion = gamesWinChampion;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getChampion() {
        return champion;
    }

    public void setChampion(Integer champion) {
        this.champion = champion;
    }

    public Integer getGamesChampion() {
        return gamesChampion;
    }

    public void setGamesChampion(Integer gamesChampion) {
        this.gamesChampion = gamesChampion;
    }

    public Integer getTotalGamesAccount() {
        return TotalGamesAccount;
    }

    public void setTotalGamesAccount(Integer totalGamesAccount) {
        TotalGamesAccount = totalGamesAccount;
    }

    public Integer getMasteryChampion() {
        return masteryChampion;
    }

    public void setMasteryChampion(Integer masteryChampion) {
        this.masteryChampion = masteryChampion;
    }

    public Integer getTotalMasteryAccount() {
        return TotalMasteryAccount;
    }

    public void setTotalMasteryAccount(Integer totalMasteryAccount) {
        TotalMasteryAccount = totalMasteryAccount;
    }

    public Integer getGamesWinChampion() {
        return GamesWinChampion;
    }

    public void setGamesWinChampion(Integer gamesWinChampion) {
        GamesWinChampion = gamesWinChampion;
    }
}
