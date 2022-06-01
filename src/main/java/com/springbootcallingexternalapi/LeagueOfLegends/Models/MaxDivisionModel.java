package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import java.sql.Timestamp;

/**
 * Model for the max division.
 */
public class MaxDivisionModel {

  private Timestamp date;

  private String tier;

  private String rank;

  private String account;

  public MaxDivisionModel() {
  }

  /**
   * Constructor for the max division.
   */
  public MaxDivisionModel(Timestamp date, String tier, String rank, String account) {
    this.date = date;
    this.tier = tier;
    this.rank = rank;
    this.account = account;
  }

  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }

  public String getTier() {
    return tier;
  }

  public void setTier(String tier) {
    this.tier = tier;
  }

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }
}
