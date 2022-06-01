package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/**Insert comment. */
public class AccountBaseModel {

  private String id;
  private String accountId;
  private String puuid;
  private String name;
  private Long revisionDate;

  /**Insert comment. */
  public AccountBaseModel(String id, String accountId, String puuid, String name,
      Long revisionDate) {
    this.id = id;
    this.accountId = accountId;
    this.puuid = puuid;
    this.name = name;
    this.revisionDate = revisionDate;
  }

  public AccountBaseModel() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getPuuid() {
    return puuid;
  }

  public void setPuuid(String puuid) {
    this.puuid = puuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getRevisionDate() {
    return revisionDate;
  }

  public void setRevisionDate(Long revisionDate) {
    this.revisionDate = revisionDate;
  }
}
