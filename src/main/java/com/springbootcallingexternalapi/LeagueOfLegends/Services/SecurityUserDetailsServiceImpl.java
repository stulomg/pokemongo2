package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityUserModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityUserMainModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.SecurityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    SecurityUserRepository securityUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SecurityUserModel user = securityUserRepository.findByUserName(userName).get();
        return SecurityUserMainModel.build(user);
    }
}
