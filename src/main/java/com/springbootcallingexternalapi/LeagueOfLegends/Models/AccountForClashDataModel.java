package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class AccountForClashDataModel {

  private String teamId;

  public AccountForClashDataModel(String teamId) {
    this.teamId = teamId;
  }

  public AccountForClashDataModel() {
  }

  public String getTeamId() {
    return teamId;
  }

  public void setTeamId(String teamId) {
    this.teamId = teamId;
  }
}
