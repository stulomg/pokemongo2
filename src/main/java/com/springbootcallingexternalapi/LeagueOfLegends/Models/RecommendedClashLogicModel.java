package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/** Model for the recommended clash logic.*/

public class RecommendedClashLogicModel {

  private Integer account;
  private Integer champion;
  private Integer gamesChampion;
  private Integer totalGamesAccount;
  private Integer masteryChampion;
  private Integer totalMasteryAccount;
  private Integer gamesWinChampion;

  public RecommendedClashLogicModel() {
  }

  /** Constructor for the recommended clash logic.*/

  public RecommendedClashLogicModel(Integer account, Integer champion, Integer gamesChampion,
      Integer totalGamesAccount, Integer masteryChampion, Integer totalMasteryAccount,
      Integer gamesWinChampion) {
    this.account = account;
    this.champion = champion;
    this.gamesChampion = gamesChampion;
    this.totalGamesAccount = totalGamesAccount;
    this.masteryChampion = masteryChampion;
    this.totalMasteryAccount = totalMasteryAccount;
    this.gamesWinChampion = gamesWinChampion;
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
    return totalGamesAccount;
  }

  public void setTotalGamesAccount(Integer totalGamesAccount) {
    this.totalGamesAccount = totalGamesAccount;
  }

  public Integer getMasteryChampion() {
    return masteryChampion;
  }

  public void setMasteryChampion(Integer masteryChampion) {
    this.masteryChampion = masteryChampion;
  }

  public Integer getTotalMasteryAccount() {
    return totalMasteryAccount;
  }

  public void setTotalMasteryAccount(Integer totalMasteryAccount) {
    this.totalMasteryAccount = totalMasteryAccount;
  }

  public Integer getGamesWinChampion() {
    return gamesWinChampion;
  }

  public void setGamesWinChampion(Integer gamesWinChampion) {
    this.gamesWinChampion = gamesWinChampion;
  }
}
