package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.SecurityUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class RiotRestControllerTest {

  private static final String RIOT_TOKEN = "RGAPI-2e7cc45a-5269-46cb-ae9c-c4bd7787e2f7";
  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  AccountRepository accountRepository;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private SecurityUserService securityUserService;

  @Test
  public void callRiotSuccessfullyDefaultCase() throws Exception {
    jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE;");

    String token = securityUserService.generateToken();
    mockMvc.perform(
            MockMvcRequestBuilders.get("/call-riot/Darkclaw/stul").header("authorization", token))
        .andExpect(status().isOk()).andReturn();
  }

  @Test
  public void getMasterySuccessfullyDefaultCase() throws Exception {
    jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE;");
    AccountBaseModel baseModel = new AccountBaseModel(
        "qwx-QX5fD0_freIh9Wai_5nVkDrZ2urz_VTfA74M9e9P",
        "nS4rwFEX4a58v9ghLVldu34nNV4_GVPLNnDJiRiLZLxG0x4",
        "dkiVwTUbuZMVmqV0T6-KIDOGTrBeeqoJhR5It3ksJ3j1UwM2Dmk1rm2NVcjyffiF-hHBtXRpnkjXAw",
        "Darkclaw",
        1653003765000L
    );
    Integer owner = 1;
    accountRepository.insertAccount(baseModel, owner);
    String token = securityUserService.generateToken();
    mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/darkclaw/evelynn")
        .header("authorization", token)).andExpect(status().isOk()).andReturn();
  }

  @Test
  public void championNotFoundExceptionGetMastery() throws Exception {
    String token = securityUserService.generateToken();
    mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/stul/urfsito")
        .header("authorization", token)).andExpect(status().isNotFound());
  }

  @Test
  public void championMasteryNotFoundExceptionGetMastery() throws Exception {
    String token = securityUserService.generateToken();
    mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/stul/renata glasc")
        .header("authorization", token)).andExpect(status().isNotFound());
  }

  @Test
  public void accountNotFoundExceptionGetMastery() throws Exception {
    String token = securityUserService.generateToken();
    mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/pepitoalim/ezreal")
        .header("authorization", token)).andExpect(status().isNotFound());
  }

  @Test
  public void characterNotAllowedExceptionGetMastery() throws Exception {
    String token = securityUserService.generateToken();
    mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/Dark*claw/ez<<real")
        .header("authorization", token)).andExpect(status().isBadRequest());
  }

  @Test
  public void accountDataExceptionEnGetMastery() {
    //no se como hacerlo
  }

  @Test
  public void serverStatusDefault() throws Exception {
    String token = securityUserService.generateToken();
    mockMvc.perform(
            MockMvcRequestBuilders.get("/call-riot/server/status").header("authorization", token))
        .andExpect(status().isOk()).andReturn();
  }

  @Test
  public void serverStatusNotFound() throws Exception {
    String token = securityUserService.generateToken();
    mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/server/").header("authorization", token))
        .andExpect(status().isNotFound()).andReturn();
  }

  @Test
  public void serverStatusCharacterNotAllowed() throws Exception {
    String token = securityUserService.generateToken();
    mockMvc.perform(
            MockMvcRequestBuilders.get("/call-riot/server/status*").header("authorization", token))
        .andExpect(status().isBadRequest()).andReturn();
  }

  @Test
  public void clashDefaultCase() throws Exception {

    String token = securityUserService.generateToken();
    mockMvc.perform(
            MockMvcRequestBuilders.get("/call-riot/clash/Darkclaw").header("authorization", token))
        .andExpect(status().isOk()).andReturn();
  }

  @Test
  public void clashAccountNotFoundCase() throws Exception {

    String token = securityUserService.generateToken();
    mockMvc.perform(
            MockMvcRequestBuilders.get("/call-riot/clash/DarkclASDASaw").header("authorization", token))
        .andExpect(status().isNotFound()).andReturn();
  }

  @Test
  public void clashCharacterNotAllowedCase() throws Exception {

    String token = securityUserService.generateToken();
    mockMvc.perform(
            MockMvcRequestBuilders.get("/call-riot/clash/Darkcla*w").header("authorization", token))
        .andExpect(status().isBadRequest()).andReturn();
  }
}