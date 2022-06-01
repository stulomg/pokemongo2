package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** Model for the security main user.*/

public class SecurityUserMainModel implements UserDetails {

  private final String name;
  private final String userName;
  private final String email;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  /** Constructor for the security main user.*/

  public SecurityUserMainModel(String name, String userName, String email, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.name = name;
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  /** Grand authority to the main user.*/

  public static SecurityUserMainModel build(SecurityUserModel user) {
    List<GrantedAuthority> authorities =
        user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
            .collect(Collectors.toList());
    return new SecurityUserMainModel(user.getName(), user.getUserName(), user.getEmail(),
        user.getPassword(), authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }
}
