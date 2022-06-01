package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/**
 * Model for the recommended clash response.
 */
public class RecommendedClashResponseModel {

  private Integer account;
  private Integer recommendPosition;
  private Integer gamesPlayed;
  private Integer recommendChampion;

  public RecommendedClashResponseModel() {
  }

  /**
   * Constructor for the recommended clash response.
   */
  public RecommendedClashResponseModel(Integer account, Integer recommendPosition,
      Integer gamesPlayed, Integer recommendChampion) {
    this.account = account;
    this.recommendPosition = recommendPosition;
    this.gamesPlayed = gamesPlayed;
    this.recommendChampion = recommendChampion;
  }

  public Integer getAccount() {
    return account;
  }

  public void setAccount(Integer account) {
    this.account = account;
  }

  public Integer getRecommendPosition() {
    return recommendPosition;
  }

  public void setRecommendPosition(Integer recommendPosition) {
    this.recommendPosition = recommendPosition;
  }

  public Integer getGamesPlayed() {
    return gamesPlayed;
  }

  public void setGamesPlayed(Integer gamesPlayed) {
    this.gamesPlayed = gamesPlayed;
  }

  public Integer getRecommendChampion() {
    return recommendChampion;
  }

  public void setRecommendChampion(Integer recommendChampion) {
    this.recommendChampion = recommendChampion;
  }
}
