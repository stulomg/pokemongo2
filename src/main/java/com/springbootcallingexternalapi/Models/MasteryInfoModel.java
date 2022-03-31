package com.springbootcallingexternalapi.Models;

public class MasteryInfoModel {

    private String championId;

    private String championLevel;

    private String championPoints;

    public MasteryInfoModel(String championId, String championLevel, String championPoints) {
        this.championId = championId;
        this.championLevel = championLevel;
        this.championPoints = championPoints;
    }

    public MasteryInfoModel() {
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
