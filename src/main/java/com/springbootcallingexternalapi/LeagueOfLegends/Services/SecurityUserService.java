package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Enums.RoleName;
import com.springbootcallingexternalapi.LeagueOfLegends.JWT.JwtProvider;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.*;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.SecurityRoleRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.SecurityUserRepository;
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
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@EnableCaching
public class SecurityUserService {
    @Autowired
    SecurityUserRepository securityUserRepository;
    @Autowired//newuser
    PasswordEncoder passwordEncoder;
    @Autowired//newuser
    SecurityRoleRepository securityRoleRepository;
    @Autowired//login
    AuthenticationManager authenticationManager;
    @Autowired//login
    JwtProvider jwtProvider;
    @Autowired// generateToken
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

    public SecurityJwtDtoModel login(SecurityLoginUserModel securityLoginUserModel) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(securityLoginUserModel.getUserName(), securityLoginUserModel.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        SecurityJwtDtoModel securityJwtDtoModel = new SecurityJwtDtoModel(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return securityJwtDtoModel;
    }

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