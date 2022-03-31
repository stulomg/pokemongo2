package com.springbootcallingexternalapi.Models;

import java.sql.Timestamp;
import java.util.Date;

public class MasteryHistoryInfoModel {


    private String championName;

    private Long championId;

    private int championLevel;

    private int championPoints;

    private Timestamp timestamp;

    private String account;

    public MasteryHistoryInfoModel(String championName, Long championId, int championLevel, int championPoints, Timestamp timestamp, String account) {
        this.championName = championName;
        this.championId = championId;
        this.championLevel = championLevel;
        this.championPoints = championPoints;
        this.timestamp = timestamp;
        this.account = account;
    }

    public MasteryHistoryInfoModel() {
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public Long getChampionId() {
        return championId;
    }

    public void setChampionId(Long championId) {
        this.championId = championId;
    }

    public int getChampionLevel() {
        return championLevel;
    }

    public void setChampionLevel(int championLevel) {
        this.championLevel = championLevel;
    }

    public int getChampionPoints() {
        return championPoints;
    }

    public void setChampionPoints(int championPoints) {
        this.championPoints = championPoints;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
