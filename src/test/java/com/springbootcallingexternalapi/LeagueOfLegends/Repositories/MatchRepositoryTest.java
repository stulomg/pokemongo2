package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.GameDataModel;
import com.springbootcallingexternalapi.SpringBootCallingExternalApiApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.List;

@SpringBootTest(classes = MatchRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
class MatchRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MatchRepository repository;
    @Autowired
    public MatchRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        repository = new MatchRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", jdbcTemplate);
    }
    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"MatchHistory\" RESTART IDENTITY CASCADE");
    }

    @Test
    void insertIndividualMatchData() {
        GameDataModel basemodel = new GameDataModel(
                "vladimir",
                "testuno",
                true,
                "top",
                500
        );
        repository.insertIndividualMatchData(basemodel,1,1,8);
        List<GameDataModel> resultSet = jdbcTemplate.query("SELECT * FROM \"MatchHistory\"", BeanPropertyRowMapper.newInstance(GameDataModel.class));
        Assertions.assertEquals(1, resultSet.size());
        GameDataModel result = resultSet.get(0);
        Assertions.assertEquals(basemodel.isWin(),result.isWin());
        Assertions.assertEquals(basemodel.getChampionPoints(),result.getChampionPoints());
    }
}