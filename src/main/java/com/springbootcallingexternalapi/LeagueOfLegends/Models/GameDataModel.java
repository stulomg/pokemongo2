package com.springbootcallingexternalapi.LeagueOfLegends.Models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**Ignore properties other than the assigned ones.*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDataModel {

  private String championName;

  private String summonerName;

  private boolean win;

  private String individualPosition;

  private int championPoints;

  /** Model for the game data model.*/

  public GameDataModel(String championName, String summonerName, boolean win,
      String individualPosition, int championPoints) {
    this.championName = championName;
    this.summonerName = summonerName;
    this.win = win;
    this.individualPosition = individualPosition;
    this.championPoints = championPoints;
  }

  public GameDataModel() {
  }

  public String getChampionName() {
    return championName;
  }

  public void setChampionName(String championName) {
    this.championName = championName;
  }

  public String getSummonerName() {
    return summonerName;
  }

  public void setSummonerName(String summonerName) {
    this.summonerName = summonerName;
  }

  public boolean isWin() {
    return win;
  }

  public void setWin(boolean win) {
    this.win = win;
  }

  public String getIndividualPosition() {
    return individualPosition;
  }

  public void setIndividualPosition(String individualPosition) {
    this.individualPosition = individualPosition;
  }

  public int getChampionPoints() {
    return championPoints;
  }

  public void setChampionPoints(int championPoints) {
    this.championPoints = championPoints;
  }
}
