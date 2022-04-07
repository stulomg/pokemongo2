package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
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
import org.springframework.test.util.ReflectionTestUtils;
import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(classes = LeagueRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
class LeagueRepositoryTest {

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
    //ACA VAN LAS DECLARACIONES PENDIENTES


    @Test
    void insertLeagueInfo() {
    }

    @Test
    void divisionHistoryCasoDefault() throws CharacterNotAllowedException, AccountDataException, AccountNotFoundException, InterruptedException {


        for (int i = 0;i<21;i++) {
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

        List <LeagueInfoModel> leagueInfoModels = repository.divisionHistory("Soyeon Lover");
        Assertions.assertEquals(20,leagueInfoModels.size());
        for (int i=0;i<leagueInfoModels.size()-1;i++){
            Assertions.assertTrue(leagueInfoModels.get(i).getDate().after(leagueInfoModels.get(i+1).getDate()));
        }
    }
    @Test
    void divisionHistoryCasoMenosDeVente() throws CharacterNotAllowedException, AccountDataException, AccountNotFoundException, InterruptedException {

        for (int i = 0; i<15;i++){
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
        List <LeagueInfoModel> leagueInfoModels = repository.divisionHistory("SummonerName");
        Assertions.assertEquals(15,leagueInfoModels.size());
    }


}