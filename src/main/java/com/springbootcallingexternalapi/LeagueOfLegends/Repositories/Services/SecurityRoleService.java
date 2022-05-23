package com.springbootcallingexternalapi.LeagueOfLegends.Repositories.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Enums.RoleName;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityRoleModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.SecurityRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class SecurityRoleService {
    @Autowired
    SecurityRoleRepository securityRoleRepository;

    public Optional<SecurityRoleModel> getByRoleName(RoleName roleName){
        return securityRoleRepository.findByRoleName(roleName);
    }
}
