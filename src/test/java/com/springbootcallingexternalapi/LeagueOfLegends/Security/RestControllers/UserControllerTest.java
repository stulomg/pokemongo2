package com.springbootcallingexternalapi.LeagueOfLegends.Security.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.Models.User;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.Repositories.UserRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.dto.NewUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    void newUser() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"user\" RESTART IDENTITY CASCADE");

        User newUser = new User(
                "test",
                "test",
                "test@gmail.com",
                passwordEncoder.encode("12345")
        );
        User user = new User(
                "test",
                "test",
                "test@gmail.com",
                passwordEncoder.encode("12345")
        );

        userRepository.save(newUser);
        List<User> resultSet = jdbcTemplate.query("SELECT * FROM \"user\"", BeanPropertyRowMapper.newInstance(User.class));
        Assertions.assertEquals(1, resultSet.size());

        Assertions.assertEquals(user.getName(), resultSet.get(0).getName());
        Assertions.assertEquals(user.getUserName(), resultSet.get(0).getUserName());
        Assertions.assertEquals(user.getEmail(), resultSet.get(0).getEmail());
    }

    @Test
    void login() throws Exception {
    }
}