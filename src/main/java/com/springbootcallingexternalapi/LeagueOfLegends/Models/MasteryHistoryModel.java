package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import java.sql.Timestamp;

/** Model for the mastery history.*/
public class MasteryHistoryModel {

  private String champion;

  private int championLevel;

  private int championPoints;

  private Timestamp date;

  private String account;

  /** Constructor for the mastery history.*/
  public MasteryHistoryModel(String champion, int championLevel, int championPoints, Timestamp date,
      String account) {
    this.champion = champion;
    this.championLevel = championLevel;
    this.championPoints = championPoints;
    this.date = date;
    this.account = account;
  }

  public MasteryHistoryModel() {
  }

  public String getChampion() {
    return champion;
  }

  public void setChampion(String champion) {
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

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }
}
