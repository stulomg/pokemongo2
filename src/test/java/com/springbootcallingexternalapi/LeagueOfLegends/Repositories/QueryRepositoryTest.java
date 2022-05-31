package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountExistsOrNotException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QueryCriteriaExistException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QueryFilterNoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QueryNoDataException;
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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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
        jdbcTemplate.execute("TRUNCATE TABLE \"SpecificQuery\" RESTART IDENTITY CASCADE");
    }

    @Test
    void newQueryDefaultCase() throws QueryCriteriaExistException {
        QueryModel newQuery = new QueryModel(
                "test prueba",
                "SELECT * FROM \"Account\""
        );
        queryRepository.newQuery(newQuery);
        List<QueryModel> result = jdbcTemplate.query("SELECT * FROM \"SpecificQuery\"", BeanPropertyRowMapper.newInstance(QueryModel.class));
        Assertions.assertEquals(1,result.size());
        Assertions.assertEquals(newQuery.getCriteria(),result.get(0).getCriteria());
        Assertions.assertEquals(newQuery.getQuery(),result.get(0).getQuery());
    }
    @Test
    void newQueryQueryCriteriaExistException() throws QueryCriteriaExistException {
        QueryModel newQuery = new QueryModel(
                "test prueba",
                "SELECT * FROM* \"Account\" WHERE"
        );
        queryRepository.newQuery(newQuery);
        Assertions.assertThrows(QueryCriteriaExistException.class, ()-> queryRepository.newQuery(newQuery));
    }

    @Test
    void specificQueryDefaultCase() throws QuerySyntaxErrorException, AccountDataException, AccountExistsOrNotException, NoDataException {
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

        List<QueryResponseModel> resultSet = queryRepository.specificQuery(newQuery);;
        Assertions.assertEquals(1, resultSet.size());

        Assertions.assertEquals(espectedResult.getId(), resultSet.get(0).getId());
        Assertions.assertEquals(espectedResult.getPuuid(), resultSet.get(0).getPuuid());
        Assertions.assertEquals(espectedResult.getAccountId(), resultSet.get(0).getAccountId());
        Assertions.assertEquals(espectedResult.getRevisionDate(), resultSet.get(0).getRevisionDate());
        Assertions.assertEquals(espectedResult.getOwner(), resultSet.get(0).getOwner());
        Assertions.assertEquals(espectedResult.getName(), resultSet.get(0).getName());
    }

    @Test
    void specificQueryNoDataException() {
        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE");
        QueryModel newQuery = new QueryModel(
                "test prueba",
                "SELECT * FROM \"Account\" WHERE name = 'testdos'"
        );
        Assertions.assertThrows(NoDataException.class, ()-> queryRepository.specificQuery(newQuery));
    }

    @Test
    void specificQuerySyntaxError() {
        QueryModel newQuery = new QueryModel(
                "test prueba",
                "SELECT * FROM* \"Account\" WHERE name = 'testdos'"
        );
        Assertions.assertThrows(QuerySyntaxErrorException.class, ()-> queryRepository.specificQuery(newQuery));
    }
    @Test
    void listQueryDefaultCase() throws QueryNoDataException, QueryCriteriaExistException {
        QueryModel newQuery = new QueryModel(
                "test prueba",
                "SELECT * FROM \"Account\""
        );
        queryRepository.newQuery(newQuery);
        List<QueryModel> result =  queryRepository.listQuery();
        Assertions.assertEquals(1,result.size());
        Assertions.assertEquals(newQuery.getCriteria(),result.get(0).getCriteria());
        Assertions.assertEquals(newQuery.getQuery(),result.get(0).getQuery());
    }
    @Test
    void ListQueryQueryNoDataException()  {
        Assertions.assertThrows(QueryNoDataException.class, ()-> queryRepository.listQuery());
    }
    @Test
    void filterQueryDefaultCase() throws  QueryCriteriaExistException, QueryFilterNoDataException {
        QueryModel newQuery = new QueryModel(
                "test prueba",
                "SELECT * FROM \"Account\""
        );
        queryRepository.newQuery(newQuery);
        QueryModel result =  queryRepository.filterQuery(newQuery.getCriteria());
        Assertions.assertEquals(newQuery.getCriteria(),result.getCriteria());
        Assertions.assertEquals(newQuery.getQuery(),result.getQuery());
    }
    @Test
    void filterQueryQueryFilterNoDataException() {
        Assertions.assertThrows(QueryFilterNoDataException.class, ()-> queryRepository.filterQuery("prueba"));
    }
}