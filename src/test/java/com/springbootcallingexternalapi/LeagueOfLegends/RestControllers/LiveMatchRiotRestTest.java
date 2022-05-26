package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameInfoBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.CurrentGameParticipantModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.RiotRequestorService;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.SecurityUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Optional;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LiveMatchRiotRestTest {

    private static final String RIOT_TOKEN = "RGAPI-179ba0a3-d7f6-44aa-af9a-ae2df3aef427";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SecurityUserService securityUserService;
    @SpyBean
    private RiotRequestorService riotRequestorService;
    @InjectMocks
    private RiotRestController riotRestController;

    @Test
    public void liveMatchSuccessfullyDefaultCase() throws Exception {

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

        CurrentGameInfoBaseModel fakeCurrentGameInfoBaseModel = new CurrentGameInfoBaseModel(
                11L,
                "CLASSIC",
                "MATCHED_GAME",
                participants
        );

        AccountBaseModel model = new AccountBaseModel(
                "uxXUjTn9WObZzjvGayVLZVwCiKGxnkX5XyXOgh9Masbp6w",
                "jsdfhaskdfh",
                "jahdfjadshf",
                "Hauries",
                2853L
        );

        doReturn(ResponseEntity.of(Optional.of(model))).when(riotRequestorService).getAccountFromRiot("hauries");

        doReturn(ResponseEntity.of(Optional.of(fakeCurrentGameInfoBaseModel))).when(riotRequestorService).requestToRiot("/lol/spectator/v4/active-games/by-summoner/uxXUjTn9WObZzjvGayVLZVwCiKGxnkX5XyXOgh9Masbp6w", HttpMethod.GET, CurrentGameInfoBaseModel.class);

        String token = securityUserService.generateToken();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/call-riot/live/match/hauries").header("authorization", token)).andExpect(status().isOk()).andReturn();

        CurrentGameInfoBaseModel response = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), CurrentGameInfoBaseModel.class);

        Assertions.assertEquals(fakeCurrentGameInfoBaseModel.toString(), response.toString());
    }
}
