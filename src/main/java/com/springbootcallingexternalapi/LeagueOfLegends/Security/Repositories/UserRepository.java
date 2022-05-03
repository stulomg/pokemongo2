package com.springbootcallingexternalapi.LeagueOfLegends.Security.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Security.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
