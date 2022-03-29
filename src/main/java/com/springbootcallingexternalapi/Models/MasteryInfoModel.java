package com.springbootcallingexternalapi.Models;

public class MasteryInfoModel {
    private String id;

    private Integer summonerLevel;

    private String championId;

    private String championLevel;

    private String championPoints;

    public MasteryInfoModel(String id, Integer summonerLevel, String championId, String championLevel, String championPoints) {
        this.id = id;
        this.summonerLevel = summonerLevel;
        this.championId = championId;
        this.championLevel = championLevel;
        this.championPoints = championPoints;
    }

    public MasteryInfoModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(Integer summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public String getChampionId() {
        return championId;
    }

    public void setChampionId(String championId) {
        this.championId = championId;
    }

    public String getChampionLevel() {
        return championLevel;
    }

    public void setChampionLevel(String championLevel) {
        this.championLevel = championLevel;
    }

    public String getChampionPoints() {
        return championPoints;
    }

    public void setChampionPoints(String championPoints) {
        this.championPoints = championPoints;
    }
}
