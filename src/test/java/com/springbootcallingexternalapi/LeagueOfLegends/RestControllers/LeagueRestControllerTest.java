package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MaxDivisionModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.LeagueRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.RiotRequestorService;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.SecurityUserService;
import java.sql.Timestamp;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class LeagueRestControllerTest {

  @Autowired
  LeagueRepository repository;
  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  RiotRequestorService riotRequestorService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private SecurityUserService securityUserService;

  @BeforeEach
  void setup() {
    jdbcTemplate.execute("TRUNCATE TABLE \"LeagueHistory\"");
  }

  @Test
  void divisionHistoryDefaultCase() throws Exception {
    for (int i = 0; i < 21; i++) {
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      Thread.sleep(100);
      LeagueInfoModel model = new LeagueInfoModel(
          timestamp,
          "id1",
          "RANKED_SOLO_5x5",
          "PLATINUM",
          "II",
          "test1",
          63,
          5,
          "s");
      repository.insertLeagueInfo(model, 1, 1);
    }

    List<LeagueInfoModel> leagueInfoModels = repository.divisionHistory("Soyeon Lover", 1);
    Assertions.assertEquals(20, leagueInfoModels.size());
    for (int i = 0; i < leagueInfoModels.size() - 1; i++) {
      Assertions.assertTrue(
          leagueInfoModels.get(i).getDate().after(leagueInfoModels.get(i + 1).getDate()));
    }
  }

  @Test
  void divisionHistoryAccountNotFountException() throws Exception {
    String token = securityUserService.generateToken();
    mockMvc.perform(get("/account/division-history/kusarin").header("authorization", token))
        .andExpect(status().isNotFound())
        .andExpect(content().string("The account kusarin was not found, please rectify"));
  }

  @Test
  void divisionHistoryLeagueNotFoundException() throws Exception {

    riotRequestorService.getAccountAndAssignToOwner("Visk", "testuno");
    String token = securityUserService.generateToken();
    mockMvc.perform(get("/account/division-history/Visk").header("authorization", token))
        .andExpect(status().isNotFound())
        .andExpect(content().string("The SoloQ data for Visk does not exist"));
  }

  @Test
  public void DivisionHistoryCharacterNotAllowedException() throws Exception {
    String token = securityUserService.generateToken();
    mockMvc.perform(get("/account/division-history/kusi>>").header("authorization", token))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("kusi>> has characters not allowed"));
  }

  @Test
  void maxDivisionDefaultCase() throws Exception {
    LeagueInfoModel infoModel = new LeagueInfoModel(
        Timestamp.valueOf("2022-03-30 22:25:28.744"),
        "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
        "RANKED_SOLO_5x5",
        "PLATINUM",
        "I",
        "testuno",
        76,
        5476,
        "testuno");

    LeagueInfoModel infoModel2 = new LeagueInfoModel(
        Timestamp.valueOf("2022-04-30 22:25:28.744"),
        "a2e93031-cf37-4b92-ae32-23b348340525",
        "RANKED_SOLO_5x5",
        "PLATINUM",
        "II",
        "testdos",
        60,
        5360,
        "testdos");

    repository.insertLeagueInfo(infoModel, 1, 1);
    repository.insertLeagueInfo(infoModel2, 2, 2);

    String token = securityUserService.generateToken();
    MvcResult mvcResult = mockMvc.perform(
            get("/account/max-division/testuno/testdos").header("authorization", token))
        .andExpect(status().isOk()).andReturn();

    String response = mvcResult.getResponse().getContentAsString();
    MaxDivisionModel[] leagueInfoModel = new ObjectMapper().readValue(response,
        MaxDivisionModel[].class);
    Assertions.assertEquals("1", leagueInfoModel[0].getAccount());
  }

  @Test
  void maxDivisionAccountNotFountException() throws Exception {
    String token = securityUserService.generateToken();
    mockMvc.perform(get("/account/max-division/kusarin/manuelin").header("authorization", token))
        .andExpect(status().isNotFound())
        .andExpect(content().string("The owner kusarin was not found, please rectify"));
  }

  @Test
  public void maxDivisionCharacterNotAllowedException() throws Exception {
    String token = securityUserService.generateToken();
    mockMvc.perform(get("/account/max-division/kusi>>/Darkclaw").header("authorization", token))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("kusi>> has characters not allowed"));
  }
}