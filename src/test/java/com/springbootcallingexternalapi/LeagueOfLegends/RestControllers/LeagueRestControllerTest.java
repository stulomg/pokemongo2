package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.LeagueRepository;
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

import java.sql.Timestamp;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class  LeagueRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    LeagueRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"Account\"");
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
}
