package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameInfoBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameParticipantModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.RiotRequestorService;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.SecurityUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;
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


    // Autowired
    // private RiotRequestorService riotRequestorService;

    @MockBean
    RestTemplate restTemplate;

    @InjectMocks
    private RiotRequestorService riotRequestorService1 = new RiotRequestorService();

    private static final String RIOT_TOKEN = "RGAPI-61e95a26-6132-4673-8a4a-afc4e53fe6b7";

    @Autowired
    private SecurityUserService securityUserService;

    @Test
    public void callRiotExitosamenteCasoDefautl() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Account\"");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "soyeon lover",
                1648276400000L
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

    @Test
    public void LiveMatchExitosoCasoDefault() throws Exception {


        CurrentGameParticipantModel participant1 = new CurrentGameParticipantModel(
                100L,
                4L,
                3L,
                202L,
                "hauries"
        );

        CurrentGameParticipantModel participant2 = new CurrentGameParticipantModel(
                200L,
                4L,
                3L,
                202L,
                "pepito"
        );

        CurrentGameParticipantModel[] participants = {participant1, participant2};

        CurrentGameInfoBaseModel fakecurrentGameInfoBaseModel = new CurrentGameInfoBaseModel(
                11L,
                "CLASSIC",
                "MATCHED_GAME",
                participants
        );

        AccountBaseModel model = new AccountBaseModel(
                "asdjkas",
                "uxXUjTn9WObZzjvGayVLZVwCiKGxnkX5XyXOgh9Masbp6w",
                "jahdfjadshf",
                "Hauries",
                2853L
        );


        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Riot-Token", RIOT_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("", headers);


        when(restTemplate.exchange("https://la1.api.riotgames.com/lol/summoner/v4/summoners/by-name/hauries", HttpMethod.GET, entity, AccountBaseModel.class))
                .thenReturn(ResponseEntity.of(Optional.of(model)));

        when(restTemplate.exchange("https://la1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/uxXUjTn9WObZzjvGayVLZVwCiKGxnkX5XyXOgh9Masbp6w", HttpMethod.GET, entity, CurrentGameInfoBaseModel.class))
                .thenReturn(ResponseEntity.of(Optional.of(fakecurrentGameInfoBaseModel)));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/live/match/hauries")).andExpect(status().isOk()).andReturn();

        CurrentGameInfoBaseModel response = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), CurrentGameInfoBaseModel.class);

        Assertions.assertEquals(fakecurrentGameInfoBaseModel.toString(), response.toString());
    }
}
