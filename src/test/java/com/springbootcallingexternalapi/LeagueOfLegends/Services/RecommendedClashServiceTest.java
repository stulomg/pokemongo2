package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.GameDataModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashDataModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashRoleModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.MatchRepository;
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

@SpringBootTest(classes = RecommendedClashService.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
class RecommendedClashServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RecommendedClashService recommendedRoleService;
    @Autowired
    private MatchRepository matchRepository;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"MatchHistory\" RESTART IDENTITY CASCADE");
    }

    @Test
    void recommendedRoleService() throws  NoDataException {
        for (int i = 0; i < 10; i++) {
            GameDataModel dataSummoner = new GameDataModel();
            dataSummoner.setWin(true);
            dataSummoner.setChampionPoints(0000);
            if (i < 5) {
                matchRepository.insertIndividualMatchData(dataSummoner,1,4,8);
            }else {
                if (i == 8) {
                    matchRepository.insertIndividualMatchData(dataSummoner,2,2,8);
                }else {
                    matchRepository.insertIndividualMatchData(dataSummoner,2,3,8);
                }
            }
        }
        List<RecommendedClashDataModel> participants = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            RecommendedClashDataModel participant = new RecommendedClashDataModel();
            if (i == 1) {
                participant.setParticipant("testuno");
            }else if (i == 2) {
                participant.setParticipant("testdos");
            }else {
                participant.setParticipant("testtres");
            }
            participants.add(participant);
        }
        ArrayList accounts = new ArrayList();
        for (int i = 0; i < 5; i++) {
            accounts.add(i);
        }
        List<RecommendedClashRoleModel> recommendedRoleResponse = recommendedRoleService.recommendedRoleService(accounts);
        Assertions.assertEquals(5, recommendedRoleResponse.size());
        for (int i = 0; i < recommendedRoleResponse.size(); i++) {
            RecommendedClashRoleModel result = recommendedRoleResponse.get(i);
            if (result.getAccount() == 1) {
                Assertions.assertEquals(4,result.getRecommendPosition());
                Assertions.assertEquals(5,result.getGamesPlayed());
            }else if (result.getAccount() == 2) {
                Assertions.assertEquals(3,result.getRecommendPosition());
                Assertions.assertEquals(4,result.getGamesPlayed());
            }else {
                Assertions.assertEquals(0,result.getRecommendPosition());
                Assertions.assertEquals(0,result.getGamesPlayed());
            }
        }
    }
}