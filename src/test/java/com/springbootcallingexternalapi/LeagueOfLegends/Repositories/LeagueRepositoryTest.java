package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedExceptionOwner;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.LeagueDataNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MaxDivisionModel;
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

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = LeagueRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)

public class LeagueRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LeagueRepository repository;
    @Autowired
    public LeagueRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        repository = new LeagueRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", jdbcTemplate);
    }

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"LeagueHistory\"");
    }

    @Test
    void insertLeagueInfo() {
    }

    @Test
    void divisionHistoryDefaultCase() throws CharacterNotAllowedException, AccountDataException, InterruptedException, LeagueDataNotFoundException {
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
                    52,
                    "s");
            repository.insertLeagueInfo(model,1,1);
        }

        List<LeagueInfoModel> leagueInfoModels = repository.divisionHistory("testuno",1);
        Assertions.assertEquals(20, leagueInfoModels.size());
        for (int i = 0; i < leagueInfoModels.size() - 1; i++) {
            Assertions.assertTrue(leagueInfoModels.get(i).getDate().after(leagueInfoModels.get(i + 1).getDate()));
        }
    }
    @Test
    void divisionHistoryLessThanTwentyDefaultCase() throws CharacterNotAllowedException, AccountDataException, InterruptedException, LeagueDataNotFoundException {
        for (int i = 0; i < 15; i++) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Thread.sleep(100);
            LeagueInfoModel leagueInfoModel = new LeagueInfoModel(
                    timestamp,
                    "LeagueID",
                    "QueueType",
                    "Tier",
                    "Rank",
                    "SummonerName",
                    58,
                    5,
                    "s");

            repository.insertLeagueInfo(leagueInfoModel, 1,1);
        }
        List<LeagueInfoModel> leagueInfoModels = repository.divisionHistory("testuno",1);
        Assertions.assertEquals(15, leagueInfoModels.size());
    }

    @Test
    void accountNotFoundExceptionDivisionHistory() {
        Assertions.assertThrows(LeagueDataNotFoundException.class, () -> repository.divisionHistory("testtres",3));
    }

    @Test
    void characterNotAllowedDivisionHistory() {
        //GIVEN
        Assertions.assertThrows(CharacterNotAllowedException.class, () -> repository.divisionHistory("te*st2",2));
    }


    @Test
    void insertLeagueInfoSuccessfullyDefaultCase() throws AccountDataException {
        //GIVEN
        LeagueInfoModel baseModel = new LeagueInfoModel(
                Timestamp.valueOf("2022-03-30 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "Darkclaw",
                76,
                5,
                "s"
        );

        //WHEN
        repository.insertLeagueInfo(baseModel, 1,1);
        //THEN
        List<LeagueInfoModel> resultSet = jdbcTemplate.query("SELECT * FROM \"LeagueHistory\"", BeanPropertyRowMapper.newInstance(LeagueInfoModel.class));
        Assertions.assertEquals(1, resultSet.size());
        LeagueInfoModel result = resultSet.get(0);
        Assertions.assertEquals(baseModel.getDate(), result.getDate());
        Assertions.assertEquals(baseModel.getLeagueId(), result.getLeagueId());
        Assertions.assertEquals(baseModel.getQueueType(), result.getQueueType());
        Assertions.assertEquals(baseModel.getTier(), result.getTier());
        Assertions.assertEquals(baseModel.getRank(), result.getRank());
        Assertions.assertEquals(baseModel.getLeaguePoints(), result.getLeaguePoints());
    }

    @Test
    void accountDataExceptionInsertLeagueInfo() throws CharacterNotAllowedException, AccountDataException, AccountNotFoundException {
        //given
        Assertions.assertThrows(AccountDataException.class, () -> {
            LeagueInfoModel baseModel = new LeagueInfoModel(
                    Timestamp.valueOf("2022-03-30 22:25:28.744"),
                    "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                    "RANKED_SOLO_5x5",
                    "PLATINUM**",
                    "I",
                    "Darkclaw",
                    76,
                    5,
                    "s"
            );
            repository.insertLeagueInfo(baseModel,5,1);
        });
    }

    @Test
    void maxDivisionDefaultCase() throws CharacterNotAllowedException, AccountDataException, CharacterNotAllowedExceptionOwner, OwnerNotFoundException {
//GIVEN
        LeagueInfoModel infoModel = new LeagueInfoModel(
                Timestamp.valueOf("2022-03-30 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "Darkclaw",
                76,
                5476,
                "testuno");

        LeagueInfoModel infoModel2 = new LeagueInfoModel(
                Timestamp.valueOf("2022-04-30 22:25:28.744"),
                "a2e93031-cf37-4b92-ae32-23b348340525",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "II",
                "Raino",
                60,
                5560,
                "testdos");
//WHEN
        repository.insertLeagueInfo(infoModel, 1,1);
        repository.insertLeagueInfo(infoModel2, 2,2);
//THEN
        List<MaxDivisionModel> resultSet = repository.getMaxDivision(infoModel.getOwner(), infoModel2.getOwner(),1,2);
        Assertions.assertEquals(1, resultSet.size());
        MaxDivisionModel result = resultSet.get(0);

        Assertions.assertEquals(infoModel2.getDate(), result.getDate());
        Assertions.assertEquals(infoModel2.getTier(), result.getTier());
        Assertions.assertEquals(infoModel2.getRank(), result.getRank());
    }

    @Test
    void OwnerNotFoundExceptionMaxDivision() {
        Assertions.assertThrows(OwnerNotFoundException.class, () -> repository.getMaxDivision("Owner", "Owner",1,1));
    }

    @Test
    void characterNotAllowedExceptionMaxDivision() {
        Assertions.assertThrows(CharacterNotAllowedExceptionOwner.class, () -> repository.getMaxDivision("O*ner", "O*ner",3,3));
    }
}
