package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.GameDataModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashDataModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashRoleModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.MatchRepository;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecommendedClashRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    SecurityUserService securityUserService;
    @Autowired
    MatchRepository matchRepository;
    @MockBean
    RestTemplate restTemplate;
    ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getRecommendedRole() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"MatchHistory\" RESTART IDENTITY CASCADE");
        for (int i = 0; i < 10; i++) {
            GameDataModel dataSummoner = new GameDataModel();
            dataSummoner.setWin(true);
            dataSummoner.setChampionPoints(0000);
            if (i < 5) {
                matchRepository.insertIndividualMatchData(dataSummoner,1,4,8);
            }else {
                if (i == 8) {
                    matchRepository.insertIndividualMatchData(dataSummoner,2,2,8);
                }else {
                    matchRepository.insertIndividualMatchData(dataSummoner,2,3,8);
                }
            }
        }
        List<RecommendedClashDataModel> participants = (List<RecommendedClashDataModel>) Stream.of(
                new RecommendedClashDataModel("testuno"),
                new RecommendedClashDataModel("testdos"),
                new RecommendedClashDataModel("testtres"),
                new RecommendedClashDataModel("testtres"),
                new RecommendedClashDataModel("testtres"))
                .collect(Collectors.toList());
        String token = securityUserService.generateToken();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/loldata/clash/recommendedRole").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participants)))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        RecommendedClashRoleModel[] recommendedRole = new ObjectMapper().readValue(response, RecommendedClashRoleModel[].class);
        for (int i = 0; i < recommendedRole.length; i++) {
            if (recommendedRole[i].getAccount() == 1) {
                Assertions.assertEquals(4, recommendedRole[i].getRecommendPosition());
                Assertions.assertEquals(5, recommendedRole[i].getGamesPlayed());
            }else if (recommendedRole[i].getAccount() == 2) {
                Assertions.assertEquals(3, recommendedRole[i].getRecommendPosition());
                Assertions.assertEquals(4, recommendedRole[i].getGamesPlayed());
            }else {
                Assertions.assertEquals(0, recommendedRole[i].getRecommendPosition());
                Assertions.assertEquals(0, recommendedRole[i].getRecommendPosition());
            }
        }
    }
    @Test
    public void NoDataException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"MatchHistory\" RESTART IDENTITY CASCADE");
        List<RecommendedClashDataModel> participants = (List<RecommendedClashDataModel>) Stream.of(
                        new RecommendedClashDataModel("testuno"),
                        new RecommendedClashDataModel("testdos"),
                        new RecommendedClashDataModel("testtres"),
                        new RecommendedClashDataModel("testtres"),
                        new RecommendedClashDataModel("testtres"))
                .collect(Collectors.toList());
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/loldata/clash/recommendedRole").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participants)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("There is not enough data to perform the query"));
    }
    @Test
    public void CharacterNotAllowedException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"MatchHistory\" RESTART IDENTITY CASCADE");
        List<RecommendedClashDataModel> participants = (List<RecommendedClashDataModel>) Stream.of(
                        new RecommendedClashDataModel("testuno*"),
                        new RecommendedClashDataModel("testdos"),
                        new RecommendedClashDataModel("testtres"),
                        new RecommendedClashDataModel("testtres"),
                        new RecommendedClashDataModel("testtres"))
                .collect(Collectors.toList());
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/loldata/clash/recommendedRole").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participants)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("testuno* has characters not allowed"));
    }
    @Test
    public void AccountNotFoundException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"MatchHistory\" RESTART IDENTITY CASCADE");
        List<RecommendedClashDataModel> participants = (List<RecommendedClashDataModel>) Stream.of(
                        new RecommendedClashDataModel("testuno"),
                        new RecommendedClashDataModel("testdos"),
                        new RecommendedClashDataModel("testtres"),
                        new RecommendedClashDataModel("testtres"),
                        new RecommendedClashDataModel("testcuatro"))
                .collect(Collectors.toList());
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/loldata/clash/recommendedRole").header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participants)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The account testcuatro was not found, please rectify"));
    }
}