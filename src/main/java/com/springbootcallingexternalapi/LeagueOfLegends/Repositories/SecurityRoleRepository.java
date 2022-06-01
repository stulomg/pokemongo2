package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Enums.RoleName;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityRoleModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Scan repository class.*/
@Repository
public interface SecurityRoleRepository extends JpaRepository<SecurityRoleModel, Integer> {

  Optional<SecurityRoleModel> findByRoleName(RoleName roleName);
}
