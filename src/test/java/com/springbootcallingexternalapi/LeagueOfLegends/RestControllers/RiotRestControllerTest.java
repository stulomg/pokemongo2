package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameInfoBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameParticipantModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import static org.mockito.Mockito.mock;
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

    private static final String RIOT_TOKEN = "RGAPI-329a974c-12b6-4300-b223-d25605a17063";



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
    public void LiveMatchExitosoCasoDefault() throws Exception {

        RestTemplate restTemplate = mock(RestTemplate.class);

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


        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Riot-Token", RIOT_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        System.out.println(entity);
        when(restTemplate.exchange("https://la1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/uxXUjTn9WObZzjvGayVLZVwCiKGxnkX5XyXOgh9Masbp6w", HttpMethod.GET,entity, CurrentGameInfoBaseModel.class))
                .thenReturn(ResponseEntity.of(Optional.of(fakecurrentGameInfoBaseModel)));


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/live/match/hauries")).andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals(fakecurrentGameInfoBaseModel,response);
    }
}
