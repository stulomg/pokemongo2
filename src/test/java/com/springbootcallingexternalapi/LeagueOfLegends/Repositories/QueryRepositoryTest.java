package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountExistsOrNotException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QueryCriteriaExistException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QuerySyntaxErrorException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.QueryModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.QueryResponseModel;
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

@SpringBootTest(classes = QueryRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
class QueryRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private QueryRepository queryRepository;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"QuerySpecific\" RESTART IDENTITY CASCADE");
    }

    @Test
    void specificQueryDefaultCase() throws QuerySyntaxErrorException, AccountDataException, AccountExistsOrNotException, QueryCriteriaExistException, NoDataException {
        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE");
        AccountBaseModel modelData = new  AccountBaseModel(
                "test",
                "test",
                "test",
                "test",
                122514L
        );
        accountRepository.insertAccount(modelData,2);

        QueryModel newQuery = new QueryModel(
                "test prueba",
                "SELECT * FROM \"Account\" WHERE name = 'test'"
        );
        QueryResponseModel espectedResult = new QueryResponseModel(
                "test",
                "test",
                "test",
                122514L,
                2,
                "test"
        );

        Object resultSet = queryRepository.specificQuery(newQuery);;
        //Assertions.assertEquals(1, resultSet.size());
    }

    @Test
    void specificQuerySyntaxError() {
        QueryModel newQuery = new QueryModel(
                "test prueba",
                "SELECT * FROM* \"Account\" WHERE name = 'testdos'"
        );
        Assertions.assertThrows(QuerySyntaxErrorException.class, ()-> queryRepository.specificQuery(newQuery));
    }
}