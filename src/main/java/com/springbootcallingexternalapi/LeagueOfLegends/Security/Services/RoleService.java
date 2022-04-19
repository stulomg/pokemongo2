package com.springbootcallingexternalapi.LeagueOfLegends.Security.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Security.Enums.RoleName;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.Models.Role;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> getByRoleName(RoleName roleName){
        return roleRepository.findByRoleName(roleName);
    }
}
