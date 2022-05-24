package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityJwtDtoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityLoginUserModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityNewUserModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityUserModel;
import com.springbootcallingexternalapi.SpringBootCallingExternalApiApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest(classes = SecurityUserService.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
class SecurityUserServiceTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    SecurityUserService securityUserService;

    @Test
    void newUser() {
        jdbcTemplate.execute("TRUNCATE TABLE \"user\" RESTART IDENTITY CASCADE");
        SecurityNewUserModel newUser = new SecurityNewUserModel(
                "test100",
                "test100",
                "test100@gmail.com",
                "12345"
        );
        SecurityUserModel userExpected = new SecurityUserModel(
                "test100",
                "test100",
                "test100@gmail.com",
                "12345"
        );
        securityUserService.newUser(newUser);
        List<SecurityUserModel> resultSet = jdbcTemplate.query("SELECT * FROM \"user\"", BeanPropertyRowMapper.newInstance(SecurityUserModel.class));
        Assertions.assertEquals(1,resultSet.size());
        Assertions.assertEquals(userExpected.getName(),resultSet.get(0).getName());
        Assertions.assertEquals(userExpected.getUserName(),resultSet.get(0).getUserName());
        Assertions.assertEquals(userExpected.getEmail(),resultSet.get(0).getEmail());
    }

    @Test
    void login() {
        jdbcTemplate.execute("TRUNCATE TABLE \"user\" RESTART IDENTITY CASCADE");
        SecurityNewUserModel newUser = new SecurityNewUserModel(
                "test100",
                "test100",
                "test100@gmail.com",
                "12345"
        );
        securityUserService.newUser(newUser);
        SecurityLoginUserModel user = new SecurityLoginUserModel(
                "test100",
                "12345"
        );
        SecurityJwtDtoModel response = securityUserService.login(user);
        Assertions.assertEquals(user.getUserName(),response.getUserName());
        Assertions.assertTrue(response.getToken().contains("Bearer "));
    }

    @Test
    void generateToken() {
        String response = securityUserService.generateToken();
        Assertions.assertTrue(response.contains("Bearer "));
    }
}