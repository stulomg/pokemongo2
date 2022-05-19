package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Enums.RoleName;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SecurityRoleRepository extends JpaRepository<SecurityRoleModel, Integer> {
    Optional<SecurityRoleModel> findByRoleName(RoleName roleName);
}
