package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.RiotRequestorService;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.SecurityUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class RiotRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RiotRequestorService riotRequestorService;

    @Autowired
    private SecurityUserService securityUserService;

    private static final String RIOT_TOKEN = "RGAPI-179ba0a3-d7f6-44aa-af9a-ae2df3aef427";

    @Test
    public void callRiotExitosamenteCasoDefautl() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Account\"");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "soyeon lover",
                4864,
                1648276400000L,
                109L
        );
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/Darkclaw/stul").header("authorization", token)).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getMasteryExitosamenteCasoDefautl() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/Darkclaw/evelynn").header("authorization", token)).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void championNotFoundExceptionEnGetMastery() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/stul/urfsito").header("authorization", token)).andExpect(status().isNotFound());
    }

    @Test
    public void championMasteryNotFoundExceptionEnGetMastery() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/stul/renata glasc").header("authorization", token)).andExpect(status().isNotFound());
    }

    @Test
    public void accountNotFoundExceptionEnGetMastery() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/pepitoalimañaquetienemañajaja/ezreal").header("authorization", token)).andExpect(status().isNotFound());
    }

    @Test
    public void characterNotAllowedExceptionEnGetMastery() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/Darkclaw/ez<<real").header("authorization", token)).andExpect(status().isBadRequest());
    }

    @Test
    public void accountDataExceptionEnGetMastery() {
        //no se como hacerlo
    }

    @Test
    public void serverStatusDefault() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/server/status").header("authorization", token)).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void serverStatusNotFound() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/server/").header("authorization", token)).andExpect(status().isNotFound()).andReturn();

    }

    @Test
    public void serverStatusCharacterNotAllowed() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/server/status*").header("authorization", token)).andExpect(status().isBadRequest()).andReturn();

    }


}
