package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Hibernate tells him to make a class table.
 */
@Entity
@Table(name = "user", schema = "public")
public class SecurityUserModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @NotNull
  private String name;
  @NotNull
  @Column(unique = true)
  private String userName;
  @NotNull
  private String email;
  @NotNull
  private String password;
  @NotNull
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<SecurityRoleModel> securityRoleModels = new HashSet<>();

  public SecurityUserModel() {
  }

  /**
   * Constructor for the security user.
   */
  public SecurityUserModel(@NotNull String name, @NotNull String userName, @NotNull String email,
      @NotNull String password) {
    this.name = name;
    this.userName = userName;
    this.email = email;
    this.password = password;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @NotNull
  public String getName() {
    return name;
  }

  public void setName(@NotNull String name) {
    this.name = name;
  }

  @NotNull
  public String getUserName() {
    return userName;
  }

  public void setUserName(@NotNull String userName) {
    this.userName = userName;
  }

  @NotNull
  public String getEmail() {
    return email;
  }

  public void setEmail(@NotNull String email) {
    this.email = email;
  }

  @NotNull
  public String getPassword() {
    return password;
  }

  public void setPassword(@NotNull String password) {
    this.password = password;
  }

  @NotNull
  public Set<SecurityRoleModel> getRoles() {
    return securityRoleModels;
  }

  public void setRoles(@NotNull Set<SecurityRoleModel> securityRoleModels) {
    this.securityRoleModels = securityRoleModels;
  }
}
