package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import com.springbootcallingexternalapi.LeagueOfLegends.Enums.RoleName;
import com.sun.istack.NotNull;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role", schema = "public")
public class SecurityRoleModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @NotNull
  @Enumerated(EnumType.STRING)
  private RoleName roleName;

  public SecurityRoleModel() {
  }

  public SecurityRoleModel(RoleName roleName) {
    this.roleName = roleName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public RoleName getRoleName() {
    return roleName;
  }

  public void setRoleName(RoleName roleName) {
    this.roleName = roleName;
  }
}
