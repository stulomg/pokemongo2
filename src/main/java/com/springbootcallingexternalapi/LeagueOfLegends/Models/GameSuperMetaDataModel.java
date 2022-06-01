package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**Ignore properties other than the assigned ones.*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameSuperMetaDataModel {


  private GameMetaDataModel info;

  public GameSuperMetaDataModel(GameMetaDataModel info) {
    this.info = info;
  }

  public GameSuperMetaDataModel() {
  }

  public GameMetaDataModel getInfo() {
    return info;
  }

  public void setInfo(GameMetaDataModel info) {
    this.info = info;
  }
}