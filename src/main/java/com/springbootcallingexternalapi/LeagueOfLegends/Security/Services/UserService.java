package com.springbootcallingexternalapi.LeagueOfLegends.Security.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Security.Models.User;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;


    public Optional<User> getByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
    public boolean existsByUserName(String userName){
        return userRepository.existsByUserName(userName);
    }
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    public void save(User user){
        userRepository.save(user);
    }
}
