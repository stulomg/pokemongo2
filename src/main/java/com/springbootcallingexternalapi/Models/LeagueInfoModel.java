package com.springbootcallingexternalapi.Models;

import java.sql.Timestamp;
import java.util.Date;

public class LeagueInfoModel {
    private Timestamp date;

    private String leagueId;

    private String queueType;

    private String tier;

    private String rank;

    private String summonerName;

    private int leaguePoints;

    public LeagueInfoModel(Timestamp date, String leagueId, String queueType, String tier, String rank, String summonerName, int leaguePoints) {
        this.date = date;
        this.leagueId = leagueId;
        this.queueType = queueType;
        this.tier = tier;
        this.rank = rank;
        this.summonerName = summonerName;
        this.leaguePoints = leaguePoints;
    }

    public LeagueInfoModel() {
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
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

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(int leaguePoints) {
        this.leaguePoints = leaguePoints;
    }
}