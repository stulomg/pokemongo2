package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MasteryHistoryModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.MasteryRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.SecurityUserService;
import java.sql.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MasteryRestControllerTest {

  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  MasteryRepository masteryRepository;
  ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private SecurityUserService securityUserService;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void accountMasteryHistory() throws Exception {
    jdbcTemplate.execute("TRUNCATE TABLE \"MasteryHistory\" RESTART IDENTITY CASCADE;");
    MasteryHistoryInfoModel baseModel = new MasteryHistoryInfoModel(
        81,
        7,
        561220,
        Timestamp.valueOf("2007-09-23 10:10:10.0"),
        1
    );
    for (int i = 0; i < 20; i++) {
      masteryRepository.insertMasteryInfo(baseModel);
    }
    String token = securityUserService.generateToken();
    MvcResult mvcResult = mockMvc.perform(
            get("/account/masteryHistory/testuno").header("authorization", token))
        .andExpect(status().isOk()).andReturn();
    String response = mvcResult.getResponse().getContentAsString();
    MasteryHistoryModel[] masteryHistory = new ObjectMapper().readValue(response,
        MasteryHistoryModel[].class);
    for (int i = 0; i < masteryHistory.length; i++) {
      Assertions.assertEquals("Ezreal", masteryHistory[i].getChampion());
      Assertions.assertEquals(baseModel.getChampionLevel(), masteryHistory[i].getChampionLevel());
      Assertions.assertEquals(baseModel.getChampionPoints(), masteryHistory[i].getChampionPoints());
      Assertions.assertEquals(baseModel.getDate(), masteryHistory[i].getDate());
      Assertions.assertEquals("testuno", masteryHistory[i].getAccount());
    }
  }

  @Test
  void CharacterNotAllowedExceptionAccountMasteryHistory() throws Exception {
    jdbcTemplate.execute("TRUNCATE TABLE \"MasteryHistory\" RESTART IDENTITY CASCADE;");
    String token = securityUserService.generateToken();
    mockMvc.perform(get("/account/masteryHistory/testuno*").header("authorization", token))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("testuno* has characters not allowed"));
  }

  @Test
  void accountNotFoundExceptionAccountMasteryHistory() throws Exception {
    jdbcTemplate.execute("TRUNCATE TABLE \"MasteryHistory\" RESTART IDENTITY CASCADE;");
    String token = securityUserService.generateToken();
    mockMvc.perform(get("/account/masteryHistory/test").header("authorization", token))
        .andExpect(status().isNotFound())
        .andExpect(content().string("The account test was not found, please rectify"));
  }
}