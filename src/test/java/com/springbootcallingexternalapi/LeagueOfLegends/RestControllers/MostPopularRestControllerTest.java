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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
    void getMostPopularDefaultCase() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"MasteryHistory\" RESTART IDENTITY CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE \"LeagueHistory\" RESTART IDENTITY CASCADE;");
        LeagueInfoModel infoModel = new LeagueInfoModel(
                Timestamp.valueOf("2022-05-21 22:25:28.744"),
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
                Timestamp.valueOf("2022-05-22 22:25:28.744"),
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
                Timestamp.valueOf("2022-05-23 22:25:28.744"),
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
                Timestamp.valueOf("2022-05-21 23:25:28.744"),
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
                Timestamp.valueOf("2022-05-24 22:25:28.744"),
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
                112,
                7,
                250,
                Timestamp.valueOf("2022-05-24 22:25:28.744"),
                1
        );
        MasteryHistoryInfoModel masteryModel2 = new MasteryHistoryInfoModel(
                8,
                7,
                156000,
                Timestamp.valueOf("2022-05-25 22:25:28.744"),
                1
        );
        MasteryHistoryInfoModel masteryModel3 = new MasteryHistoryInfoModel(
                112,
                7,
                20000,
                Timestamp.valueOf("2022-05-26 22:25:28.744"),
                1
        );
        MasteryHistoryInfoModel masteryModel4 = new MasteryHistoryInfoModel(
                112,
                7,
                155000,
                Timestamp.valueOf("2022-05-27 22:25:28.744"),
                1
        );
        MasteryHistoryInfoModel masteryModel5 = new MasteryHistoryInfoModel(
                8,
                7,
                450000,
                Timestamp.valueOf("2022-05-28 22:25:28.744"),
                1
        );
        MostPopularModel espectedResult = new MostPopularModel(
                1,
                8,
                "May 21"
                //Timestamp.valueOf("2022-04-01 00:00:00")
        );

        repositoryLegue.insertLeagueInfo(infoModel, 1,1);
        repositoryLegue.insertLeagueInfo(infoModel2, 1,1);
        repositoryLegue.insertLeagueInfo(infoModel3, 1,1);
        repositoryLegue.insertLeagueInfo(infoModel4, 2,2);
        repositoryLegue.insertLeagueInfo(infoModel5, 2,2);
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
        Assertions.assertEquals(espectedResult.getChampion(), mostPopular[0].getChampion());
        Assertions.assertEquals(espectedResult.getDate(), mostPopular[0].getDate());
    }

    @Test
    public void getMostPopularNoDataException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"MasteryHistory\" RESTART IDENTITY CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE \"LeagueHistory\" RESTART IDENTITY CASCADE;");
        String token = securityUserService.generateToken();
        mockMvc.perform(get("/loldata/mostpopular").header("authorization", token)).andExpect(status().isNotFound()).andExpect(content().string("There is not enough data to perform the query"));
    }

}



