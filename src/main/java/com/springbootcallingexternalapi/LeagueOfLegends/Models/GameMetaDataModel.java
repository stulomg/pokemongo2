package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**Ignore properties other than the assigned ones.*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameMetaDataModel {

  private GameDataModel[] participants;

  public GameMetaDataModel(GameDataModel[] participants) {
    this.participants = participants;
  }

  public GameMetaDataModel() {
  }

  public GameDataModel[] getParticipants() {
    return participants;
  }

  public void setParticipants(GameDataModel[] participants) {
    this.participants = participants;
  }
}