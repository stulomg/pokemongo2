package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.GameDataModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashRoleModel;
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


@SpringBootTest(classes = RecommendedClashRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
class RecommendedClashRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RecommendedClashRepository recommendedClashRepository;
    @Autowired
    private MatchRepository matchRepository;
    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"MatchHistory\" RESTART IDENTITY CASCADE");
    }

    @Test
    void recommendedRoleDefaultCase() throws NoDataException, CharacterNotAllowedException, AccountNotFoundException {
        for (int i = 0; i < 15; i++) {
            GameDataModel dataSummoner = new GameDataModel();
            dataSummoner.setWin(true);
            dataSummoner.setChampionPoints(0000);
            if (i < 5) {
                matchRepository.insertIndividualMatchData(dataSummoner,1,4,8);
            }else if (i < 10) {
                matchRepository.insertIndividualMatchData(dataSummoner,2,2,8);
            } else {
                matchRepository.insertIndividualMatchData(dataSummoner,3,5,8);
            }
        }
        ArrayList data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i >= 3) {
                data.add(3);
            }
            data.add(i);
        }
        List<RecommendedClashRoleModel> resultSet = recommendedClashRepository.recommendedRole(data);
        Assertions.assertEquals(3, resultSet.size());
        for (int i = 0; i < resultSet.size() ; i++) {
            RecommendedClashRoleModel result = resultSet.get(i);
            if (result.getAccount() == 1) {
                Assertions.assertEquals(1,result.getAccount());
                Assertions.assertEquals(4,result.getRecommendPosition());
                Assertions.assertEquals(5,result.getGamesPlayed());
            }else  if (result.getAccount() == 2){
                Assertions.assertEquals(2,result.getAccount());
                Assertions.assertEquals(2,result.getRecommendPosition());
                Assertions.assertEquals(5,result.getGamesPlayed());
            }else if (result.getAccount() == 3){
                Assertions.assertEquals(3,result.getAccount());
                Assertions.assertEquals(5,result.getRecommendPosition());
                Assertions.assertEquals(5,result.getGamesPlayed());
            }
        }
    }

    @Test
    void recommendedRoleNoDataException() {
        ArrayList accounts = new ArrayList();
        for (int i = 0; i < 10; i+=2) {
            accounts.add(i);
        }
        Assertions.assertThrows(NoDataException.class, () -> recommendedClashRepository.recommendedRole(accounts));
    }
}