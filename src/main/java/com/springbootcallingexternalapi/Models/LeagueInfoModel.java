package com.springbootcallingexternalapi.Models;
public class LeagueInfoModel {
    private String leagueId;

    private String queueType;

    private String tier;

    private String rank;

    private String summonerName;

    private Integer leaguePoints;

    public LeagueInfoModel(String leagueId, String queueType, String tier, String rank, String summonerName, Integer leaguePoints) {
        this.leagueId = leagueId;
        this.queueType = queueType;
        this.tier = tier;
        this.rank = rank;
        this.summonerName = summonerName;
        this.leaguePoints = leaguePoints;
    }

    public LeagueInfoModel() {
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public Integer getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(Integer leaguePoints) {
        this.leaguePoints = leaguePoints;
    }
}
