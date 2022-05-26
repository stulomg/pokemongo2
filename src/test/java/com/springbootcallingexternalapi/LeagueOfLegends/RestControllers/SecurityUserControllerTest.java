package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.*;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.SecurityUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SecurityUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    SecurityUserService securityUserService;
    @MockBean
    RestTemplate restTemplate;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @BeforeEach
    void newUserDefaultCase() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"user\" RESTART IDENTITY CASCADE");

        SecurityNewUserModel newUser = new SecurityNewUserModel(
                "test",
                "test",
                "test@gmail.com",
                "12345"
        );
        SecurityUserModel userExpected = new SecurityUserModel(
                "test",
                "test",
                "test@gmail.com",
                "12345"
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated());
        List<SecurityUserModel> resultSet = jdbcTemplate.query("SELECT * FROM \"user\"", BeanPropertyRowMapper.newInstance(SecurityUserModel.class));
        Assertions.assertEquals(1,resultSet.size());

        Assertions.assertEquals(userExpected.getName(),resultSet.get(0).getName());
        Assertions.assertEquals(userExpected.getUserName(),resultSet.get(0).getUserName());
        Assertions.assertEquals(userExpected.getEmail(),resultSet.get(0).getEmail());
    }
    @Test
    void newUserDataIntegrityViolationException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"user\" RESTART IDENTITY CASCADE");

        SecurityNewUserModel newUser = new SecurityNewUserModel(
                "test",
                "test",
                "test@gmail.com",
                "12345"
        );
        securityUserService.newUser(newUser);
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/newUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"message\":\"The username or email is already registered\"}"));
    }

    @Test
    void loginDefaultCase() throws Exception {
        SecurityLoginUserModel user = new SecurityLoginUserModel(
                "test",
                "12345"
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk()).andReturn();
    }
}