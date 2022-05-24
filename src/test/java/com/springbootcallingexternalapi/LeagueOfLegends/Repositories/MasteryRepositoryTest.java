package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;


import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MasteryHistoryModel;
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

@SpringBootTest(classes = MasteryRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
public class MasteryRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MasteryRepository repository;

    @Autowired
    public MasteryRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        repository = new MasteryRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", jdbcTemplate);
    }

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"MasteryHistory\" RESTART IDENTITY CASCADE");
    }

    @Test
    void insertMasteryInfoSuccessfullyDefaultCase() throws AccountDataException {

        MasteryHistoryInfoModel basemodel = new MasteryHistoryInfoModel(

                81,
                7,
                561220,
                Timestamp.valueOf("2007-09-23 10:10:10.0"),
                1
        );

        MasteryHistoryInfoModel modelo = new MasteryHistoryInfoModel(
                81,
                7,
                561220,
                Timestamp.valueOf("2007-09-23 10:10:10.0"),
                1
        );

        repository.insertMasteryInfo(basemodel);
        List<MasteryHistoryInfoModel> resultSet = jdbcTemplate.query("SELECT * FROM \"MasteryHistory\"", BeanPropertyRowMapper.newInstance(MasteryHistoryInfoModel.class));
        Assertions.assertEquals(1, resultSet.size());
        MasteryHistoryInfoModel result = resultSet.get(0);

        Assertions.assertEquals(modelo.getChampion(), result.getChampion());
        Assertions.assertEquals(modelo.getChampionLevel(), result.getChampionLevel());
        Assertions.assertEquals(modelo.getChampionPoints(), result.getChampionPoints());
        Assertions.assertEquals(modelo.getAccount(), result.getAccount());
    }

    @Test
    void accountDataExceptionInsertMasteryHistory() throws AccountDataException {

        MasteryHistoryInfoModel basemodel = new MasteryHistoryInfoModel(

                3,
                7,
                561220,
                Timestamp.valueOf("2007-09-23 10:10:10.0"),
                10
        );
        Exception exception = assertThrows(AccountDataException.class, () -> repository.insertMasteryInfo(basemodel));

        String expectedMessage = "The data entered for the account ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void AccountMasteryHistorySuccessfullyDefaultCase() throws AccountDataException, CharacterNotAllowedException, AccountNotFoundException {

        MasteryHistoryInfoModel basemodel = new MasteryHistoryInfoModel(

                89,
                7,
                561220,
                Timestamp.valueOf("2007-09-23 10:10:10.0"),
                1
        );
        MasteryHistoryInfoModel basemodel2 = new MasteryHistoryInfoModel(
                17,
                5,
                41429,
                Timestamp.valueOf("2007-09-23 10:11:10.0"),
                1
        );

        repository.insertMasteryInfo(basemodel);
        repository.insertMasteryInfo(basemodel2);

        List<MasteryHistoryModel> resultSet = repository.AccountMasteryHistory("testuno", 1);
        Assertions.assertEquals(2, resultSet.size());
        MasteryHistoryModel result = resultSet.get(0);

        Assertions.assertEquals(basemodel.getChampionLevel(), result.getChampionLevel());
        Assertions.assertEquals(basemodel.getChampionPoints(), result.getChampionPoints());
        Assertions.assertEquals("testuno", result.getAccount());
    }

    @Test
    void accountNotFoundExceptionAccountMasteryHistory() throws AccountDataException {

        MasteryHistoryInfoModel basemodel = new MasteryHistoryInfoModel(

                89,
                7,
                561220,
                Timestamp.valueOf("2007-09-23 10:10:10.0"),
                1
        );
        repository.insertMasteryInfo(basemodel);
        String account = "pepito";

        Exception exception = assertThrows(AccountNotFoundException.class, () -> repository.AccountMasteryHistory(account, 50));

        String expectedMessage = " The account pepito was not found, please rectify";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void characterNotAllowedAccountMasteryHistory() throws AccountDataException {

        MasteryHistoryInfoModel basemodel = new MasteryHistoryInfoModel(

                89,
                7,
                561220,
                Timestamp.valueOf("2007-09-23 10:10:10.0"),
                1
        );
        repository.insertMasteryInfo(basemodel);
        String account = "<<<";

        Exception exception = assertThrows(CharacterNotAllowedException.class, () -> repository.AccountMasteryHistory(account, 50));

        String expectedMessage = " has characters not allowed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
