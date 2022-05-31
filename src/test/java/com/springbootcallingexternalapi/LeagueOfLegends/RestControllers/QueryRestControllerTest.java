package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.*;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
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
class QueryRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    SecurityUserService securityUserService;
    @Autowired
    AccountRepository accountRepository;
    @MockBean
    RestTemplate restTemplate;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void queryDefaultCase() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"QuerySpecific\" RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE");
        AccountBaseModel modelData = new  AccountBaseModel(
                "test",
                "test",
                "test",
                "test",
                122514L
        );
        accountRepository.insertAccount(modelData,2);

        QueryModel newQuery = new QueryModel(
                "test prueba",
                "select * from \"Account\" where name = 'test'"
        );
        QueryResponseModel expectedResult = new QueryResponseModel(
                "test",
                "test",
                "test",
                122514L,
                2,
                "test"
        );
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/loldata/query").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newQuery)))
                .andExpect(status().isOk());
        List<SpecificQueryModel> resultSet = jdbcTemplate.query("SELECT * FROM \"QuerySpecific\"", BeanPropertyRowMapper.newInstance(SpecificQueryModel.class));
        Assertions.assertEquals(1,resultSet.size());

        Assertions.assertEquals(newQuery.getCriteria(),resultSet.get(0).getCriteria());
    }
    @Test
    void querySyntaxErrorExceptionNewQuery() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"QuerySpecific\" RESTART IDENTITY CASCADE");

        QueryModel newQuery = new QueryModel(
                "test prueba",
                "select * from Account* where name = 'test'"
        );
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/loldata/query").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newQuery)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error in the syntax of the query : test prueba, check the data entered"));
    }
    @Test
    void queryInvalidParameterExceptionNewQuery() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"QuerySpecific\" RESTART IDENTITY CASCADE");

        QueryModel newQuery = new QueryModel(
                "test prueba",
                "INSERT * from Account where name = 'test'"
        );
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/loldata/query").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newQuery)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid parameter in the query : test prueba, check the data entered"));
    }
}