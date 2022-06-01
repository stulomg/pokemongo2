package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/** Model for the most popular.*/
public class MostPopularModel {

  private Integer account;
  private Integer champion;
  private String date;

  public MostPopularModel() {
  }

  /** Constructor for the most popular.*/
  public MostPopularModel(Integer account, Integer champion, String date) {
    this.account = account;
    this.champion = champion;
    this.date = date;
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

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
