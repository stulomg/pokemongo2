package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/** Model for the account. */
public class AccountModel {

  private String id;

  private String accountId;

  private String puuid;

  private String name;

  private Long revisionDate;

  private Integer owner;

  private String ownerName;

  /** Constructor for the model. */
  public AccountModel(String id, String accountId, String puuid, String name, Long revisionDate,
      Integer owner, String ownerName) {
    this.id = id;
    this.accountId = accountId;
    this.puuid = puuid;
    this.name = name;
    this.revisionDate = revisionDate;
    this.owner = owner;
    this.ownerName = ownerName;
  }

  /** Constructor for the model. */
  public AccountModel(String id, String accountId, String puuid, String name, Long revisionDate,
      Integer owner) {
    this.id = id;
    this.accountId = accountId;
    this.puuid = puuid;
    this.name = name;
    this.revisionDate = revisionDate;
    this.owner = owner;

  }

  public AccountModel() {
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

  public Integer getOwner() {
    return owner;
  }

  public void setOwner(Integer owner) {
    this.owner = owner;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }
}
