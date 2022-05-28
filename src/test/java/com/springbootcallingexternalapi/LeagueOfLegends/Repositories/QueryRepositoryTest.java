package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QuerySyntaxErrorException;
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
import java.util.List;

@SpringBootTest(classes = QueryRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
class QueryRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private QueryRepository queryRepository;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"QuerySpecific\" RESTART IDENTITY CASCADE");
    }

    @Test
    void specificQueryDefaultCase() throws QuerySyntaxErrorException {
        QueryModel newQuery = new QueryModel(
                "test prueba",
                "*",
                "Account",
                ""
        );
        String sql = "SELECT * FROM \"Account\" WHERE name = 'testdos'";
        QueryResponseModel espectedResult = new QueryResponseModel(
                "testdos",
                "testdos",
                "testdos",
                123457L,
                2,
                "testdos"
        );
        List<QueryResponseModel> resultSet = queryRepository.querySpecific(newQuery,sql);;
        Assertions.assertEquals(1, resultSet.size());
        QueryResponseModel result = resultSet.get(0);

        Assertions.assertEquals(espectedResult.getId(), result.getId());
        Assertions.assertEquals(espectedResult.getPuuid(), result.getPuuid());
        Assertions.assertEquals(espectedResult.getAccountId(), result.getAccountId());
        Assertions.assertEquals(espectedResult.getRevisionDate(), result.getRevisionDate());
        Assertions.assertEquals(espectedResult.getOwner(), result.getOwner());
        Assertions.assertEquals(espectedResult.getName(), result.getName());
    }

    @Test
    void specificQuerySyntaxError() {
        QueryModel newQuery = new QueryModel(
                "test prueba",
                "*",
                "Account",
                ""
        );
        String sql = "SELECT * FROM* \"Account\" WHERE name = 'testdos'";
        Assertions.assertThrows(QuerySyntaxErrorException.class, ()-> queryRepository.querySpecific(newQuery,sql));
    }
}