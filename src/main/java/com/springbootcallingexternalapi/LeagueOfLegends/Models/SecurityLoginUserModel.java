package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import javax.validation.constraints.NotBlank;

/** Model for the security user login.*/

public class SecurityLoginUserModel {

  @NotBlank
  private String userName;
  @NotBlank
  private String password;

  public SecurityLoginUserModel(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
