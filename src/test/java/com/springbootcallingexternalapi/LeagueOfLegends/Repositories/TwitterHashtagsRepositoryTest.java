package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;


import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.DbNotAvaliableException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.SpringBootCallingExternalApiApplication;
import com.springbootcallingexternalapi.Twitter.TwitterRepository.HashtagsRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest(classes = HashtagsRepository.class)

@ExtendWith(SpringExtension.class)

@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)

public class TwitterHashtagsRepositoryTest {

  @Autowired

  private JdbcTemplate jdbcTemplate;

  @Autowired

  private HashtagsRepository hashtagsRepository;


  @Test
  void insertHashtagSuccessfullyDefaultCase()
      throws DbNotAvaliableException, CharacterNotAllowedException {

    String test = "#pepito";

    List<String> expectedResponse = new ArrayList<String>();
    expectedResponse.add("#RiotGames");
    expectedResponse.add("#RitoGames");
    expectedResponse.add("#Valorant");
    expectedResponse.add("#LeagueOfLegends");
    expectedResponse.add("#pepito");

    hashtagsRepository.insertHashtags(test);

    List<String> resultSet = jdbcTemplate.queryForList(
        "SELECT \"hashtagName\" FROM \"TwitterHashtag\";", String.class);

    Assertions.assertEquals(5, resultSet.size());

    Assertions.assertEquals(expectedResponse.get(0), resultSet.get(0));
    Assertions.assertEquals(expectedResponse.get(1), resultSet.get(1));
    Assertions.assertEquals(expectedResponse.get(2), resultSet.get(2));
    Assertions.assertEquals(expectedResponse.get(3), resultSet.get(3));
    Assertions.assertEquals(expectedResponse.get(4), resultSet.get(4));
  }
}