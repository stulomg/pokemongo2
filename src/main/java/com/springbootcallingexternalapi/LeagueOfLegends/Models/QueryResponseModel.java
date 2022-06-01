package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/** Model for the query response.*/
public class QueryResponseModel {

  private String id;

  private String puuid;

  private String accountId;

  private Long revisionDate;

  private Integer owner;

  private String name;

  public QueryResponseModel() {
  }

  /** Constructor for the query response.*/
  public QueryResponseModel(String id, String puuid, String accountId, Long revisionDate,
      Integer owner, String name) {
    this.id = id;
    this.puuid = puuid;
    this.accountId = accountId;
    this.revisionDate = revisionDate;
    this.owner = owner;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPuuid() {
    return puuid;
  }

  public void setPuuid(String puuid) {
    this.puuid = puuid;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public Long getRevisionDate() {
    return revisionDate;
  }

  public void setRevisionDate(Long revisionDate) {
    this.revisionDate = revisionDate;
  }

  public Integer getOwner() {
    return owner;
  }

  public void setOwner(Integer owner) {
    this.owner = owner;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
