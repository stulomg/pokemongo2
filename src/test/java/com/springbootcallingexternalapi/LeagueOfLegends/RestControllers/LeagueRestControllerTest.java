package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.LeagueRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Timestamp;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LeagueRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    LeagueRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"LeagueInfo\"");
    }

    @Test
    void divisionHistory() throws Exception {

        for (int i = 0; i < 21; i++) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Thread.sleep(100);
            LeagueInfoModel model = new LeagueInfoModel(
                    timestamp,
                    "id1",
                    "RANKED_SOLO_5x5",
                    "PLATINUM",
                    "II",
                    "Soyeon Lover",
                    63,
                    5,
                    "s");
            repository.insertLeagueInfo(model, model.getOwner());
        }

        List<LeagueInfoModel> leagueInfoModels = repository.divisionHistory("Soyeon Lover");
        Assertions.assertEquals(20, leagueInfoModels.size());
        for (int i = 0; i < leagueInfoModels.size() - 1; i++) {
            Assertions.assertTrue(leagueInfoModels.get(i).getDate().after(leagueInfoModels.get(i + 1).getDate()));
        }
    }

    @Test
    void maxDivision() throws Exception {

        LeagueInfoModel infoModel = new LeagueInfoModel(
                Timestamp.valueOf("2022-03-30 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "Darkclaw",
                76,
                5476,
                "stul");

        LeagueInfoModel infoModel2 = new LeagueInfoModel(
                Timestamp.valueOf("2022-04-30 22:25:28.744"),
                "a2e93031-cf37-4b92-ae32-23b348340525",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "II",
                "Raino",
                60,
                5360,
                "kusi");

        repository.insertLeagueInfo(infoModel, infoModel.getOwner());
        repository.insertLeagueInfo(infoModel2, infoModel2.getOwner());

        MvcResult mvcResult = mockMvc.perform(get("/account/max-division/kusi/stul")).andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        LeagueInfoModel[] leagueInfoModel = new ObjectMapper().readValue(response, LeagueInfoModel[].class);
        Assertions.assertEquals(infoModel.getSummonerName(), leagueInfoModel[0].getSummonerName());

    }

    @Test
    void maxDivisionAccountNotFount() throws Exception {

        mockMvc.perform(get("/account/max-division/kusarin/manuelin")).andExpect(status().isNotFound()).andExpect(content().string("EL OWNER kusarin Y EL OWNER manuelin NO FUERON ENCONTRADOS, POR FAVOR RECTIFICAR"));
    }

    @Test
    public void maxDivisionCharacterNotAllowedException() throws Exception {

        mockMvc.perform(get("/account/max-division/kusi>>/Darkclaw")).andExpect(status().isBadRequest()).andExpect(content().string("kusi>> or Darkclaw has characters not allowed"));
    }
}
