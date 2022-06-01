package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Enums.RoleName;
import com.springbootcallingexternalapi.LeagueOfLegends.JWT.JwtProvider;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityJwtDtoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityLoginUserModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityNewUserModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityRoleModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityUserModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.SecurityRoleRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.SecurityUserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class host the functions for security login.
 */
@Service
@Transactional
@EnableCaching
public class SecurityUserService {

  @Autowired
  SecurityUserRepository securityUserRepository;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  SecurityRoleRepository securityRoleRepository;
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  JwtProvider jwtProvider;
  @Autowired
  JdbcTemplate jdbcTemplate;

  public Optional<SecurityUserModel> getByUserName(String userName) {
    return securityUserRepository.findByUserName(userName);
  }

  public boolean existsByUserName(String userName) {
    return securityUserRepository.existsByUserName(userName);
  }

  public boolean existsByEmail(String email) {
    return securityUserRepository.existsByEmail(email);
  }

  public void save(SecurityUserModel user) {
    securityUserRepository.save(user);
  }

  /**
   * This function allows you to create a new user for login.
   */
  public void newUser(SecurityNewUserModel newUser) {
    SecurityUserModel user =
        new SecurityUserModel(newUser.getName(),
            newUser.getUserName(),
            newUser.getEmail(),
            passwordEncoder.encode(newUser.getPassword()));
    Set<SecurityRoleModel> securityRoleModels = new HashSet<>();
    securityRoleModels.add(securityRoleRepository.findByRoleName(RoleName.ROLE_USER).get());
    if (newUser.getRoles().contains("admin")) {
      securityRoleModels.add(securityRoleRepository.findByRoleName(RoleName.ROLE_ADMIN).get());
    }
    user.setRoles(securityRoleModels);
    securityUserRepository.save(user);
  }

  /**
   * This function logs an user.
   */
  public SecurityJwtDtoModel login(SecurityLoginUserModel securityLoginUserModel) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(securityLoginUserModel.getUserName(),
                securityLoginUserModel.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtProvider.generateToken(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    SecurityJwtDtoModel securityJwtDtoModel = new SecurityJwtDtoModel(jwt,
        userDetails.getUsername(), userDetails.getAuthorities());
    return securityJwtDtoModel;
  }

  /**
   * This function generate a token for authentication .
   */
  public String generateToken() {
    jdbcTemplate.execute("TRUNCATE TABLE \"user\" RESTART IDENTITY CASCADE");
    SecurityNewUserModel dataNewUser = new SecurityNewUserModel(
        "test",
        "test",
        "test@gmail.com",
        "12345"
    );
    SecurityLoginUserModel user = new SecurityLoginUserModel(
        "test",
        "12345"
    );
    newUser(dataNewUser);
    SecurityJwtDtoModel res = login(user);
    return res.getToken();
  }
}