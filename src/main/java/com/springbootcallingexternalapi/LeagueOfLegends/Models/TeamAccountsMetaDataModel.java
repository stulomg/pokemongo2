package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/**
 * Model for the team accounts meta data.
 */
public class TeamAccountsMetaDataModel {

  private TeamAccountsForClashDataModel[] players;

  public TeamAccountsMetaDataModel(TeamAccountsForClashDataModel[] players) {
    this.players = players;
  }

  public TeamAccountsMetaDataModel() {
  }

  public TeamAccountsForClashDataModel[] getPlayers() {
    return players;
  }

  public void setPlayers(TeamAccountsForClashDataModel[] players) {
    this.players = players;
  }
}
