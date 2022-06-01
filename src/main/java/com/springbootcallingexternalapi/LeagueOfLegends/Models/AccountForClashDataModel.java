package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/** Model for the clash account. */

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
