package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.ChampionsExceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.SpringBootCallingExternalApiApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest(classes = ChampionRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)

public class ChampionRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ChampionRepository repository;

    @Autowired
    public ChampionRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        repository = new ChampionRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", jdbcTemplate);
    }


    @Test
    void solicitarExitosamenteCasoDefault() throws CharacterNotAllowedException, AccountDataException, ChampionNotFoundException, ChampionMasteryNotFoundException, AccountNotFoundException {
        //GIVEN A NORMAL ACCOUNT WITH ALL DATA AND A STANDARD OWNER FROM THE BASE OWNERS
        String championNameGiven = new String(
                "zeri"

        );
        String champioIdExpected = new String(
                "221"
        );
        //Account with all data


        //WHEN TRYING TO INSERT IT INTO THE DATABASE AND RETRIEVING THE RESULTS
        repository.retrieveChampionIdByChampionName(championNameGiven);
        //THEN INSERT PROPERLY AND PERSIST THE DATA IN THE DATABASE.

        Long result = repository.retrieveChampionIdByChampionName(championNameGiven);
        Assertions.assertEquals(champioIdExpected, result.toString());


    }

    @Test
    void solicitarConErrorCaracteresRancios() {
        //GIVEN
        Assertions.assertThrows(CharacterNotAllowedException.class, () -> {
            String championNameGiven = new String(
                    "ze*/ri"

            );
            repository.retrieveChampionIdByChampionName(championNameGiven);
        });
    }

    @Test
    void solicitarConChampionInexistente() throws CharacterNotAllowedException, AccountDataException, AccountNotFoundException {
        //given
        Assertions.assertThrows(ChampionNotFoundException.class, () -> {
            String championNameGiven = new String(
                    "campeonnoexistente"
            );
            repository.retrieveChampionIdByChampionName(championNameGiven);
        });
    }
}
