package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameInfoBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameParticipantModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.PlatformDataModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.RiotRequestorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private RiotRequestorService riotRequestorService;


    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init(){
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }


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

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/stul/stul")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getMasteryExitosamenteCasoDefautl() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/stul/ezreal")).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void championNotFoundExceptionEnGetMastery () throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/stul/urfsito")).andExpect(status().isNotFound());
    }

    @Test
    public void championMasteryNotFoundExceptionEnGetMastery () throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/stul/renata glasc")).andExpect(status().isNotFound());
    }

    @Test
    public void accountNotFoundExceptionEnGetMastery() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/pepitoalimañaquetienemañajaja/ezreal")).andExpect(status().isNotFound());
    }

    @Test
    public void characterNotAllowedExceptionEnGetMastery() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/mastery/stul/ez<<real")).andExpect(status().isBadRequest());
    }

    @Test
    public void accountDataExceptionEnGetMastery(){
        //no se como hacerlo
    }

    @Test
    public void LiveMatchExitosoCasoDefault() throws JsonProcessingException, URISyntaxException, CharacterNotAllowedException, AccountNotFoundException {

        CurrentGameParticipantModel participant1 = new CurrentGameParticipantModel(
                100L,
                4L,
                7L,
                81L,
                "DarkClaw"
        );

        CurrentGameParticipantModel participant2 = new CurrentGameParticipantModel(
                200L,
                4L,
                12L,
                420L,
                "Vantiax"
        );

        CurrentGameParticipantModel[] participants = {participant1, participant2};

        CurrentGameInfoBaseModel platformDataModel = new CurrentGameInfoBaseModel(
                11L,
                "CLASSIC",
                "MATCHED_GAME",
                participants
        );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI("https://la1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/859p-3luPoNXveKakUo2mj7RQoBwuYE2PWIgJZx4SGZt")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                .body(mapper.writeValueAsString(platformDataModel))
                );

        CurrentGameInfoBaseModel modelo = riotRequestorService.getLiveMatch("Darkclaw");

        Assertions.assertEquals(platformDataModel,modelo);
    }


}
