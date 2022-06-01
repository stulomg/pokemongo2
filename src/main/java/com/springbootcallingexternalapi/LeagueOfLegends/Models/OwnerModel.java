package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class OwnerModel {

  private String name;

  public OwnerModel(String name) {
    this.name = name;
  }

  public OwnerModel() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
