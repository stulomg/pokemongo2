package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
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

@SpringBootTest(classes = LeagueRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)

public class LeagueRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private LeagueRepository repository;


        @Autowired
        public LeagueRepositoryTest(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            repository = new LeagueRepository();
            ReflectionTestUtils.setField(repository, "jdbcTemplate", jdbcTemplate);
        }

        @BeforeEach
        void setup() {
            jdbcTemplate.execute("TRUNCATE TABLE \"LeagueInfo\"");
        }


        @Test
        void insertLeagueInfo() {
        }

        @Test
        void divisionHistoryCasoDefault() throws CharacterNotAllowedException, AccountDataException, AccountNotFoundException, InterruptedException {


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
                        63);
                repository.insertLeagueInfo(model);
            }

            List<LeagueInfoModel> leagueInfoModels = repository.divisionHistory("Soyeon Lover");
            Assertions.assertEquals(20, leagueInfoModels.size());
            for (int i = 0; i < leagueInfoModels.size() - 1; i++) {
                Assertions.assertTrue(leagueInfoModels.get(i).getDate().after(leagueInfoModels.get(i + 1).getDate()));
            }
        }

        @Test
        void divisionHistoryCasoMenosDeVente() throws CharacterNotAllowedException, AccountDataException, AccountNotFoundException, InterruptedException {

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
                        58);

                repository.insertLeagueInfo(leagueInfoModel);

            }
            List<LeagueInfoModel> leagueInfoModels = repository.divisionHistory("SummonerName");
            Assertions.assertEquals(15, leagueInfoModels.size());
        }


    @Test
    void insertarExitosamenteCasoDefault() throws CharacterNotAllowedException, AccountDataException, AccountNotFoundException {
        //GIVEN
        LeagueInfoModel baseModel = new LeagueInfoModel(
                Timestamp.valueOf("2022-03-30 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "Darkclaw",
                76

        );

        //WHEN
        repository.insertLeagueInfo(baseModel);
        //THEN
        List<LeagueInfoModel> resultSet = jdbcTemplate.query("SELECT * FROM \"LeagueInfo\"", BeanPropertyRowMapper.newInstance(LeagueInfoModel.class));
        Assertions.assertEquals(1, resultSet.size());
        LeagueInfoModel result = resultSet.get(0);
        Assertions.assertEquals(baseModel.getDate(), result.getDate());
        Assertions.assertEquals(baseModel.getLeagueId(), result.getLeagueId());
        Assertions.assertEquals(baseModel.getQueueType(), result.getQueueType());
        Assertions.assertEquals(baseModel.getTier(), result.getTier());
        Assertions.assertEquals(baseModel.getRank(), result.getRank());
        Assertions.assertEquals(baseModel.getSummonerName(), result.getSummonerName());
        Assertions.assertEquals(baseModel.getLeaguePoints(), result.getLeaguePoints());


    }

    @Test
    void insertarConFalloCasoCaracteresRancios() throws CharacterNotAllowedException, AccountDataException, AccountNotFoundException {
        //GIVEN
        Assertions.assertThrows(CharacterNotAllowedException.class, () -> {
            LeagueInfoModel baseModel = new LeagueInfoModel(
                    Timestamp.valueOf("2022-03-30 22:25:28.744"),
                    "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                    "RANKED_SOLO_5x5",
                    "PLATINUM",
                    "I",
                    "Dark/*claw",
                    76
            );
            //WHEN IS PROVED BY THE FUNCTION
            repository.insertLeagueInfo(baseModel);
        });
        List<LeagueInfoModel> resultSet = jdbcTemplate.query("SELECT * FROM \"LeagueInfo\"", BeanPropertyRowMapper.newInstance(LeagueInfoModel.class));
        Assertions.assertEquals(0, resultSet.size());
    }

    @Test
    void insertarConDatoNuloEnCuentaDeRiot() throws CharacterNotAllowedException, AccountDataException, AccountNotFoundException {
        //GIVEN
        Assertions.assertThrows(NullPointerException.class, () -> {
            LeagueInfoModel baseModel = new LeagueInfoModel(
                    Timestamp.valueOf("2022-03-30 22:25:28.744"),
                    "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                    "RANKED_SOLO_5x5",
                    "PLATINUM",
                    "I",
                    null,
                    76

            );
            repository.insertLeagueInfo(baseModel);
        });
    }

    @Test
    void insertarConErrorEnBaseDeDatos() throws CharacterNotAllowedException, AccountDataException, AccountNotFoundException{
        //given
        Assertions.assertThrows(AccountDataException.class, () -> {
            LeagueInfoModel baseModel = new LeagueInfoModel(
                    Timestamp.valueOf("2022-03-30 22:25:28.744"),
                    "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                    "RANKED_SOLO_5x5",
                    "PLATINUM",
                    "I",
                    "Darkclaw",
                    76

            );
            repository.insertLeagueInfo(baseModel);
            repository.insertLeagueInfo(baseModel);
        });
    }
}
