package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.*;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.QueryRepository;
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
import org.springframework.test.web.servlet.MvcResult;
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
    @Autowired
    QueryRepository queryRepository;
    @MockBean
    RestTemplate restTemplate;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void newQueryDefaultCase() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"SpecificQuery\" RESTART IDENTITY CASCADE");
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
        mockMvc.perform(MockMvcRequestBuilders.post("/loldata/newquery").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newQuery)))
                .andExpect(status().isOk());
        List<QueryModel> resultSet = jdbcTemplate.query("SELECT * FROM \"SpecificQuery\"", BeanPropertyRowMapper.newInstance(QueryModel.class));
        Assertions.assertEquals(1,resultSet.size());

        Assertions.assertEquals(newQuery.getCriteria(),resultSet.get(0).getCriteria());
        Assertions.assertEquals(newQuery.getQuery(),resultSet.get(0).getQuery());
    }
    @Test
    void newQueryNoDataException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"SpecificQuery\" RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE");
        QueryModel newQuery = new QueryModel(
                "test prueba",
                "select * from \"Account\" where name = 'test'"
        );
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/loldata/newquery").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newQuery)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is not enough data to perform the query"));
    }
    @Test
    void newQuerySyntaxErrorExceptionNewQuery() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"SpecificQuery\" RESTART IDENTITY CASCADE");

        QueryModel newQuery = new QueryModel(
                "test prueba",
                "select * from Account* where name = 'test'"
        );
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/loldata/newquery").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newQuery)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error in the syntax of the query : test prueba, check the data entered"));
    }
    @Test
    void newQueryInvalidParameterExceptionNewQuery() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"SpecificQuery\" RESTART IDENTITY CASCADE");

        QueryModel newQuery = new QueryModel(
                "test prueba",
                "INSERT * from Account where name = 'test'"
        );
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/loldata/newquery").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newQuery)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid parameter in the query : test prueba, check the data entered"));
    }
    @Test
    void newQueryQueryCriteriaExistException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"SpecificQuery\" RESTART IDENTITY CASCADE");
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
                "test",
                "select * from \"Account\""
        );
        queryRepository.newQuery(newQuery);
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/loldata/newquery").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newQuery)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The criteria: test, is already registered, please use another name"));
    }

    @Test
    void listQueryDefaultCase() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"SpecificQuery\" RESTART IDENTITY CASCADE");
        QueryModel newQuery = new QueryModel(
                "test prueba",
                "select * from \"Account\" where name = 'test'"
        );
        QueryModel newQuery2 = new QueryModel(
                "test prueba2",
                "select * from \"Account\" where name = 'test'"
        );
        queryRepository.newQuery(newQuery);
        queryRepository.newQuery(newQuery2);
        String token = securityUserService.generateToken();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/loldata/listquery").header("authorization", token))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        QueryModel[] recommendedRole = new ObjectMapper().readValue(response, QueryModel[].class);

        Assertions.assertEquals(newQuery.getCriteria(), recommendedRole[0].getCriteria());
        Assertions.assertEquals(newQuery.getQuery(), recommendedRole[0].getQuery());
        Assertions.assertEquals(newQuery2.getCriteria(), recommendedRole[1].getCriteria());
        Assertions.assertEquals(newQuery2.getQuery(), recommendedRole[1].getQuery());
    }
    @Test
    void listQueryQueryNoDataException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"SpecificQuery\" RESTART IDENTITY CASCADE");
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/loldata/listquery").header("authorization", token))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There are currently no queries stored"));
    }

    @Test
    void queryDefaultCase() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"SpecificQuery\" RESTART IDENTITY CASCADE");
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
                "test",
                "select * from \"Account\" where name = 'test'"
        );
        queryRepository.newQuery(newQuery);

        String token = securityUserService.generateToken();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/loldata/query/test").header("authorization", token))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        QueryResponseModel[] recommendedRole = new ObjectMapper().readValue(response, QueryResponseModel[].class);

        Assertions.assertEquals(modelData.getId(), recommendedRole[0].getId());
        Assertions.assertEquals(modelData.getPuuid(), recommendedRole[0].getPuuid());
        Assertions.assertEquals(modelData.getAccountId(), recommendedRole[0].getAccountId());
        Assertions.assertEquals(modelData.getRevisionDate(), recommendedRole[0].getRevisionDate());
        Assertions.assertEquals(modelData.getName(), recommendedRole[0].getName());
        Assertions.assertEquals(2, recommendedRole[0].getOwner());
    }
    @Test
    void queryQueryFilterNoDataException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"SpecificQuery\" RESTART IDENTITY CASCADE");
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/loldata/query/test").header("authorization", token))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no query with the criteria: test"));
    }
    @Test
    void queryNoDataException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"SpecificQuery\" RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE");
        QueryModel newQuery = new QueryModel(
                "test",
                "select * from \"Account\" where name = 'test'"
        );
        queryRepository.newQuery(newQuery);
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/loldata/query/test").header("authorization", token))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is not enough data to perform the query"));
    }
    @Test
    void queryQuerySyntaxErrorException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"SpecificQuery\" RESTART IDENTITY CASCADE");
        QueryModel newQuery = new QueryModel(
                "test",
                "select * from** \"Account\" where name = 'test'"
        );
        queryRepository.newQuery(newQuery);
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/loldata/query/test").header("authorization", token))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error in the syntax of the query : test, check the data entered"));
    }
}