package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MostPopularModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.LeagueRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.MasteryRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
                Timestamp.valueOf("2022-04-01 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "vantiax",
                76,
                5476,
                "stul"
        );
        MasteryHistoryInfoModel masteryModel = new MasteryHistoryInfoModel(
                "viktor",
                81L,
                7,
                250,
                Timestamp.valueOf("2022-03-20 22:25:28.744"),
                "vantiax"
        );
        MostPopularModel espectedResult = new MostPopularModel(
                "vantiax",
                "viktor",
                Timestamp.valueOf("2022-04-01 00:00:00")
        );

        repositoryLegue.insertLeagueInfo(infoModel, infoModel.getOwner());
        repositoryMaster.insertMasteryInfo(masteryModel);
        mockMvc.perform(get("/loldata/mostpopular")).andExpect(status().isOk());
        MvcResult mvcResult = mockMvc.perform(get("/loldata/mostpopular")).andExpect(status().isOk()).andReturn();

        /*mockMvc.perform(get("/loldata/mostpopular")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(espectedResult)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.account").value("vantiax"))
                .andDo(print());
                */

        //String content = mvcResult.getResponse().getContentAsString();
        //Assert.assertNotNull(content);
        //System.out.println(content);

        //assertEquals(espectedResult,content);
    }
    @Test
    public void NoDataException() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE \"AccountMasteryHistory\"");
        jdbcTemplate.execute("TRUNCATE TABLE \"LeagueInfo\"");
        //mockMvc.perform(MockMvcRequestBuilders.get("/loldata/mostpopular")).andExpect(status().isBadRequest());
    }
}

