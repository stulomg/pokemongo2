package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.*;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameInfoBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameParticipantModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.RiotRequestorService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
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

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/Darkclaw/stul")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getMasteryExitosamenteCasoDefautl() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/Darkclaw/evelynn")).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void championNotFoundExceptionEnGetMastery () throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/stul/urfsito")).andExpect(status().isNotFound());
    }

    @Test
    public void championMasteryNotFoundExceptionEnGetMastery() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/stul/renata glasc")).andExpect(status().isNotFound());
    }

    @Test
    public void accountNotFoundExceptionEnGetMastery() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/pepitoalimañaquetienemañajaja/ezreal")).andExpect(status().isNotFound());
    }

    @Test
    public void characterNotAllowedExceptionEnGetMastery() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/Darkclaw/ez<<real")).andExpect(status().isBadRequest());
    }

    @Test
    public void accountDataExceptionEnGetMastery(){
        //no se como hacerlo
    }

    @Test
    public void serverStatusDefault() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/server/status")).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void serverStatusNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/server/")).andExpect(status().isNotFound()).andReturn();

    }

    @Test
    public void serverStatusCharacterNotAllowed() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/server/status*")).andExpect(status().isBadRequest()).andReturn();

    }


}
