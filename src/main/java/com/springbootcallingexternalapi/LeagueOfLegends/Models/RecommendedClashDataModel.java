package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/** Model for the recommended clash data.*/
public class RecommendedClashDataModel {

  private String participant;

  public RecommendedClashDataModel() {
  }

  public RecommendedClashDataModel(String participant) {
    this.participant = participant;
  }

  public String getParticipant() {
    return participant;
  }

  public void setParticipant(String participant) {
    this.participant = participant;
  }
}
