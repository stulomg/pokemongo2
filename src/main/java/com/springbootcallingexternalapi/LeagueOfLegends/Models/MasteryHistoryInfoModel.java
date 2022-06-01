package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import java.sql.Timestamp;

/**
 * Model for the mastery history.
 */
public class MasteryHistoryInfoModel {

  private Integer champion;

  private int championLevel;

  private int championPoints;

  private Timestamp date;

  private Integer account;

  /**
   * Constructor for the mastery history.
   */
  public MasteryHistoryInfoModel(Integer champion, int championLevel, int championPoints,
      Timestamp date, Integer account) {
    this.champion = champion;
    this.championLevel = championLevel;
    this.championPoints = championPoints;
    this.date = date;
    this.account = account;
  }

  public MasteryHistoryInfoModel() {
  }

  public Integer getChampion() {
    return champion;
  }

  public void setChampion(Integer champion) {
    this.champion = champion;
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

  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }

  public Integer getAccount() {
    return account;
  }

  public void setAccount(Integer account) {
    this.account = account;
  }
}
