package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Enums.RoleName;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityRoleModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.SecurityRoleRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class host the services of roles for login accounts.
 */
@Service
@Transactional
public class SecurityRoleService {

  @Autowired
  SecurityRoleRepository securityRoleRepository;

  public Optional<SecurityRoleModel> getByRoleName(RoleName roleName) {
    return securityRoleRepository.findByRoleName(roleName);
  }
}
