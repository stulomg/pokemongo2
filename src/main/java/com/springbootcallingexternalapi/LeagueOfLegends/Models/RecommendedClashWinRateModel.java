package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class RecommendedClashWinRateModel {

  private Integer account;
  private Integer champion;
  private Integer win;

  public RecommendedClashWinRateModel() {
  }

  public RecommendedClashWinRateModel(Integer account, Integer champion, Integer win) {
    this.account = account;
    this.champion = champion;
    this.win = win;
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

  public Integer getWin() {
    return win;
  }

  public void setWin(Integer win) {
    this.win = win;
  }
}
