package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.GameDataModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedRoleDataModel;
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

import java.util.ArrayList;
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
        jdbcTemplate.execute("TRUNCATE TABLE \"MatchHistory\" RESTART IDENTITY CASCADE");
    }

    @Test
    void recommendedRole() throws NoDataException, CharacterNotAllowedException, AccountNotFoundException {
        GameDataModel dataModel = new GameDataModel(
                "viktor",
                "vantiax",
                true,
                "mid",
                1235
        );
        GameDataModel dataModel2 = new GameDataModel(
                "viktor",
                "vantiax",
                true,
                "mid",
                1235
        );
        GameDataModel dataModel3 = new GameDataModel(
                "viktor",
                "vantiax",
                true,
                "mid",
                1235
        );
        GameDataModel dataModel4 = new GameDataModel(
                "ezreal",
                "stul",
                true,
                "adc",
                1235
        );
        GameDataModel dataModel5 = new GameDataModel(
                "ezreal",
                "stul",
                true,
                "adc",
                1235
        );
        RecommendedRoleModel espectedResult = new RecommendedRoleModel(
                1,
                3,
                3
        );
        RecommendedRoleModel espectedResult2 = new RecommendedRoleModel(
                2,
                4,
                2
        );
        RecommendedRoleModel espectedResult3 = new RecommendedRoleModel(
                3,
                0,
                0
        );
        RecommendedRoleModel espectedResult4 = new RecommendedRoleModel(
                3,
                0,
                0
        );
        RecommendedRoleModel espectedResult5 = new RecommendedRoleModel(
                3,
                0,
                0
        );
        RecommendedRoleDataModel data1 = new RecommendedRoleDataModel(
                "testuno"
        );
        RecommendedRoleDataModel data2 = new RecommendedRoleDataModel(
                "testdos"
        );
        RecommendedRoleDataModel data3 = new RecommendedRoleDataModel(
                "testtres"
        );
        RecommendedRoleDataModel data4 = new RecommendedRoleDataModel(
                "testtres"
        );
        RecommendedRoleDataModel data5 = new RecommendedRoleDataModel(
                "testtres"
        );
        List<RecommendedRoleDataModel> data = new ArrayList<>();
        data.add(data1);
        data.add(data2);
        data.add(data3);
        data.add(data4);
        data.add(data5);

        matchRepository.insertMatchData(dataModel,1,3,112);
        matchRepository.insertMatchData(dataModel2,1,3,112);
        matchRepository.insertMatchData(dataModel3,1,3,112);
        matchRepository.insertMatchData(dataModel4,2,4,89);
        matchRepository.insertMatchData(dataModel5,2,4,89);

        List<RecommendedRoleModel> resultSet = recommendedRoleService.recommendedRoleService(data);
        Assertions.assertEquals(5, resultSet.size());
        RecommendedRoleModel result = resultSet.get(0);
        RecommendedRoleModel result2 = resultSet.get(1);
        RecommendedRoleModel result3 = resultSet.get(2);
        RecommendedRoleModel result4 = resultSet.get(3);
        RecommendedRoleModel result5 = resultSet.get(4);

        Assertions.assertEquals(espectedResult.getAccount(), result.getAccount());
        Assertions.assertEquals(espectedResult.getRecommendPosition(), result.getRecommendPosition());
        Assertions.assertEquals(espectedResult.getGamesPlayed(), result.getGamesPlayed());

        Assertions.assertEquals(espectedResult2.getAccount(), result2.getAccount());
        Assertions.assertEquals(espectedResult2.getRecommendPosition(), result2.getRecommendPosition());
        Assertions.assertEquals(espectedResult2.getGamesPlayed(), result2.getGamesPlayed());

        Assertions.assertEquals(espectedResult3.getAccount(), result3.getAccount());
        Assertions.assertEquals(espectedResult3.getRecommendPosition(), result3.getRecommendPosition());
        Assertions.assertEquals(espectedResult3.getGamesPlayed(), result3.getGamesPlayed());

        Assertions.assertEquals(espectedResult4.getAccount(), result4.getAccount());
        Assertions.assertEquals(espectedResult4.getRecommendPosition(), result4.getRecommendPosition());
        Assertions.assertEquals(espectedResult4.getGamesPlayed(), result4.getGamesPlayed());

        Assertions.assertEquals(espectedResult5.getAccount(), result5.getAccount());
        Assertions.assertEquals(espectedResult5.getRecommendPosition(), result5.getRecommendPosition());
        Assertions.assertEquals(espectedResult5.getGamesPlayed(), result5.getGamesPlayed());

    }

    @Test
    void NoDataException() {
        ArrayList accounts = new ArrayList();
        for (int i = 0; i < 10; i+=2) {
            accounts.add(i);
        }
        Assertions.assertThrows(NoDataException.class, () -> recommendedRoleRepository.recommendedRole(accounts));
    }
}