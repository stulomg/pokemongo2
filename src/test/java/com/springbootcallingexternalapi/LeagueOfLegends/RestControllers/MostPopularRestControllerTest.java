package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.*;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.LeagueRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.MasteryRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.SecurityUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MostPopularRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private LeagueRepository repositoryLegue;

    @Autowired
    private MasteryRepository repositoryMaster;

    @Autowired
    private SecurityUserService securityUserService;

    ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getMostPopular() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"AccountMasteryHistory\"");
        jdbcTemplate.execute("TRUNCATE TABLE \"LeagueInfo\"");
        LeagueInfoModel infoModel = new LeagueInfoModel(
                Timestamp.valueOf("2022-04-21 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "vantiax",
                76,
                5476,
                "stul"
        );
        LeagueInfoModel infoModel2 = new LeagueInfoModel(
                Timestamp.valueOf("2022-04-22 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "darkclaw",
                76,
                5476,
                "stul"
        );
        LeagueInfoModel infoModel3 = new LeagueInfoModel(
                Timestamp.valueOf("2022-04-23 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "darkclaw",
                76,
                5476,
                "stul"
        );
        LeagueInfoModel infoModel4 = new LeagueInfoModel(
                Timestamp.valueOf("2022-04-21 23:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "vantiax",
                76,
                5476,
                "kusi"
        );
        LeagueInfoModel infoModel5 = new LeagueInfoModel(
                Timestamp.valueOf("2022-04-24 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "vantiax",
                76,
                5476,
                "kusi"
        );
        MasteryHistoryInfoModel masteryModel = new MasteryHistoryInfoModel(
                "viktor",
                81L,
                7,
                250,
                Timestamp.valueOf("2022-04-24 22:25:28.744"),
                "vantiax"
        );
        MasteryHistoryInfoModel masteryModel2 = new MasteryHistoryInfoModel(
                "vladimir",
                81L,
                7,
                156000,
                Timestamp.valueOf("2022-04-25 22:25:28.744"),
                "vantiax"
        );
        MasteryHistoryInfoModel masteryModel3 = new MasteryHistoryInfoModel(
                "viktor",
                81L,
                7,
                20000,
                Timestamp.valueOf("2022-04-26 22:25:28.744"),
                "vantiax"
        );
        MasteryHistoryInfoModel masteryModel4 = new MasteryHistoryInfoModel(
                "viktor",
                81L,
                7,
                155000,
                Timestamp.valueOf("2022-04-27 22:25:28.744"),
                "vantiax"
        );
        MasteryHistoryInfoModel masteryModel5 = new MasteryHistoryInfoModel(
                "vladimir",
                81L,
                7,
                450000,
                Timestamp.valueOf("2022-04-28 22:25:28.744"),
                "vantiax"
        );
        MostPopularModel espectedResult = new MostPopularModel(
                "vantiax",
                "vladimir",
                "Apr 21"
                //Timestamp.valueOf("2022-04-01 00:00:00")
        );

        repositoryLegue.insertLeagueInfo(infoModel, infoModel.getOwner());
        repositoryLegue.insertLeagueInfo(infoModel2, infoModel2.getOwner());
        repositoryLegue.insertLeagueInfo(infoModel3, infoModel3.getOwner());
        repositoryLegue.insertLeagueInfo(infoModel4, infoModel4.getOwner());
        repositoryLegue.insertLeagueInfo(infoModel5, infoModel5.getOwner());
        repositoryMaster.insertMasteryInfo(masteryModel);
        repositoryMaster.insertMasteryInfo(masteryModel2);
        repositoryMaster.insertMasteryInfo(masteryModel3);
        repositoryMaster.insertMasteryInfo(masteryModel4);
        repositoryMaster.insertMasteryInfo(masteryModel5);

        String token = securityUserService.generateToken();
        MvcResult mvcResult = mockMvc.perform(get("/loldata/mostpopular").header("authorization", token)).andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        MostPopularModel[] mostPopular = new ObjectMapper().readValue(response, MostPopularModel[].class);
        Assertions.assertEquals(espectedResult.getAccount(), mostPopular[0].getAccount());
        Assertions.assertEquals(espectedResult.getChampionName(), mostPopular[0].getChampionName());
        Assertions.assertEquals(espectedResult.getDate(), mostPopular[0].getDate());

    }

    @Test
    public void NoDataException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"AccountMasteryHistory\"");
        jdbcTemplate.execute("TRUNCATE TABLE \"LeagueInfo\"");
        String token = securityUserService.generateToken();
        mockMvc.perform(get("/loldata/mostpopular").header("authorization", token)).andExpect(status().isNotFound()).andExpect(content().string("There is not enough data to perform the query"));

    }

}



