package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityUserModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Scan repository class.
 */
@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUserModel, Integer> {

  Optional<SecurityUserModel> findByUserName(String userName);

  boolean existsByUserName(String userName);

  boolean existsByEmail(String email);
}