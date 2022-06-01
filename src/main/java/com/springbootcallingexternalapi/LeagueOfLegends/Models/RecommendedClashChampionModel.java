package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/** Model for the recommended clash champion.*/

public class RecommendedClashChampionModel {

  private Integer account;
  private Integer champion;
  private Integer gamesplayed;
  private Integer maxmastery;

  public RecommendedClashChampionModel() {
  }

  /** Constructor for the recommended clash champion.*/

  public RecommendedClashChampionModel(Integer account, Integer champion, Integer gamesplayed,
      Integer maxmastery) {
    this.account = account;
    this.champion = champion;
    this.gamesplayed = gamesplayed;
    this.maxmastery = maxmastery;
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

  public Integer getGamesplayed() {
    return gamesplayed;
  }

  public void setGamesplayed(Integer gamesplayed) {
    this.gamesplayed = gamesplayed;
  }

  public Integer getMaxmastery() {
    return maxmastery;
  }

  public void setMaxmastery(Integer maxmastery) {
    this.maxmastery = maxmastery;
  }
}
