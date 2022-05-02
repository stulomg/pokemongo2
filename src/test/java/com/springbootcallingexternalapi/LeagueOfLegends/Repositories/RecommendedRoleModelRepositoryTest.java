package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.GameDataModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedRoleModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.RecommendedRoleService;
import com.springbootcallingexternalapi.SpringBootCallingExternalApiApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@SpringBootTest(classes = RecommendedRoleRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
class RecommendedRoleModelRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RecommendedRoleRepository recommendedRoleRepository;
    @Autowired
    private RecommendedRoleService recommendedRoleService;
    @Autowired
    private MatchRepository matchRepository;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"Match\"");
    }

    @Test
    void recommendedRole() throws NoDataException {
        GameDataModel dataModel = new GameDataModel(
                "viktor",
                "vantiax",
                true,
                "mid",
                "mid",
                1235
        );
        GameDataModel dataModel2 = new GameDataModel(
                "viktor",
                "vantiax",
                true,
                "mid",
                "mid",
                1235
        );
        GameDataModel dataModel3 = new GameDataModel(
                "viktor",
                "vantiax",
                true,
                "mid",
                "mid",
                1235
        );
        GameDataModel dataModel4 = new GameDataModel(
                "ezreal",
                "stul",
                true,
                "adc",
                "adc",
                1235
        );
        GameDataModel dataModel5 = new GameDataModel(
                "ezreal",
                "stul",
                true,
                "adc",
                "adc",
                1235
        );
        RecommendedRoleModel espectedResult = new RecommendedRoleModel(
                "vantiax",
                "mid",
                3
        );
        RecommendedRoleModel espectedResult2 = new RecommendedRoleModel(
                "stul",
                "adc",
                2
        );
        RecommendedRoleModel espectedResult3 = new RecommendedRoleModel(
                "kusara",
                "not enough information",
                0
        );
        RecommendedRoleModel espectedResult4 = new RecommendedRoleModel(
                "raino",
                "not enough information",
                0
        );
        RecommendedRoleModel espectedResult5 = new RecommendedRoleModel(
                "darkclaw",
                "not enough information",
                0
        );

        matchRepository.insertMatchData(dataModel);
        matchRepository.insertMatchData(dataModel2);
        matchRepository.insertMatchData(dataModel3);
        matchRepository.insertMatchData(dataModel4);
        matchRepository.insertMatchData(dataModel5);

        List<RecommendedRoleModel> resultSet = recommendedRoleService.recommendedRoleRepository("vantiax","stul","kusara","raino","darkclaw");
        Assertions.assertEquals(5, resultSet.size());
        RecommendedRoleModel result = resultSet.get(0);
        RecommendedRoleModel result2 = resultSet.get(1);
        RecommendedRoleModel result3 = resultSet.get(2);
        RecommendedRoleModel result4 = resultSet.get(3);
        RecommendedRoleModel result5 = resultSet.get(4);

        Assertions.assertEquals(espectedResult.getSummonerName(), result.getSummonerName());
        Assertions.assertEquals(espectedResult.getRecommendPosition(), result.getRecommendPosition());
        Assertions.assertEquals(espectedResult.getGamesPlayed(), result.getGamesPlayed());

        Assertions.assertEquals(espectedResult2.getSummonerName(), result2.getSummonerName());
        Assertions.assertEquals(espectedResult2.getRecommendPosition(), result2.getRecommendPosition());
        Assertions.assertEquals(espectedResult2.getGamesPlayed(), result2.getGamesPlayed());

        Assertions.assertEquals(espectedResult3.getSummonerName(), result3.getSummonerName());
        Assertions.assertEquals(espectedResult3.getRecommendPosition(), result3.getRecommendPosition());
        Assertions.assertEquals(espectedResult3.getGamesPlayed(), result3.getGamesPlayed());

        Assertions.assertEquals(espectedResult4.getSummonerName(), result4.getSummonerName());
        Assertions.assertEquals(espectedResult4.getRecommendPosition(), result4.getRecommendPosition());
        Assertions.assertEquals(espectedResult4.getGamesPlayed(), result4.getGamesPlayed());

        Assertions.assertEquals(espectedResult5.getSummonerName(), result5.getSummonerName());
        Assertions.assertEquals(espectedResult5.getRecommendPosition(), result5.getRecommendPosition());
        Assertions.assertEquals(espectedResult5.getGamesPlayed(), result5.getGamesPlayed());

    }

    @Test
    void NoDataException() {

        Assertions.assertThrows(NoDataException.class, () -> recommendedRoleRepository.recommendedRole("vantiax","stul","kusara","darkclaw","raino"));
    }
}