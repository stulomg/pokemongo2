package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;


import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityUserModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.SecurityUserRepository;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SecurityUserModelControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    SecurityUserRepository securityUserRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;



    @Test
    void newUser() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"user\" RESTART IDENTITY CASCADE");

        SecurityUserModel newUser = new SecurityUserModel(
                "test",
                "test",
                "test@gmail.com",
                passwordEncoder.encode("12345")
        );
        SecurityUserModel user = new SecurityUserModel(
                "test",
                "test",
                "test@gmail.com",
                passwordEncoder.encode("12345")
        );

        securityUserRepository.save(newUser);
        List<SecurityUserModel> resultSet = jdbcTemplate.query("SELECT * FROM \"user\"", BeanPropertyRowMapper.newInstance(SecurityUserModel.class));
        Assertions.assertEquals(1,resultSet.size());

        Assertions.assertEquals(user.getName(),resultSet.get(0).getName());
        Assertions.assertEquals(user.getUserName(),resultSet.get(0).getUserName());
        Assertions.assertEquals(user.getEmail(),resultSet.get(0).getEmail());
    }

    @Test
    void login() throws Exception {
    }
}