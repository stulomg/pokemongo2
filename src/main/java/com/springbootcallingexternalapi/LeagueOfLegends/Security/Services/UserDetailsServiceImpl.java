package com.springbootcallingexternalapi.LeagueOfLegends.Security.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Security.Models.User;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.Models.UserMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.getByUserName(userName).get();
        return UserMain.build(user);
    }
}
