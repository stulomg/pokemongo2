package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.OwnerService;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.SecurityUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
class OwnerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    SecurityUserService securityUserService;
    @Autowired
    OwnerService ownerService;
    @MockBean
    RestTemplate restTemplate;
    ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void newOwnerDefaultCase() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"Owner\" RESTART IDENTITY CASCADE");
        OwnerModel newOwner = new OwnerModel(
                "test"
        );
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/account/new-owner").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOwner)))
                .andExpect(status().isOk());
        List<OwnerModel> resultSet = jdbcTemplate.query("SELECT * FROM \"Owner\"", BeanPropertyRowMapper.newInstance(OwnerModel.class));
        Assertions.assertEquals(1,resultSet.size());

        Assertions.assertEquals(newOwner.getName(),resultSet.get(0).getName());
    }
    @Test
    void ownerAlreadyExistsExceptionNewOwner() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"Owner\" RESTART IDENTITY CASCADE");
        OwnerModel newOwner = new OwnerModel(
                "test"
        );
        ownerService.insertOwner(newOwner);
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/account/new-owner").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOwner)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Owner already registered"));
    }
    @Test
    void characterNotAllowedExceptionNewOwner() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"Owner\" RESTART IDENTITY CASCADE");
        OwnerModel newOwner = new OwnerModel(
                "test*"
        );
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/account/new-owner").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOwner)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("test* has characters not allowed"));
    }
}