package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Position.PositionNotFoundException;
import com.springbootcallingexternalapi.SpringBootCallingExternalApiApplication;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = PositionRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
class PositionRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private PositionRepository positionRepository;

  @Test
  void retrievePositionIdByPositionName()
      throws CharacterNotAllowedException, PositionNotFoundException {
    ArrayList positions = new ArrayList<>();
    positions.add("TOP");
    positions.add("JUNGLE");
    positions.add("MIDDLE");
    positions.add("BOTTOM");
    positions.add("UTILITY");
    for (int i = 0; i < 5; i++) {
      Long resultSet = positionRepository.retrievePositionIdByPositionName(
          (String) positions.get(i));
      Assertions.assertEquals(i + 1, resultSet);
    }
  }

  @Test
  void positionNotFoundExceptionRetrievePositionIdByPositionName() {
    Assertions.assertThrows(PositionNotFoundException.class, () -> {
      String positionNameGiven = "midonomid";
      positionRepository.retrievePositionIdByPositionName(positionNameGiven);
    });
  }
}