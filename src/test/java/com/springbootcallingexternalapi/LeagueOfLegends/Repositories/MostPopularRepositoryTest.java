package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MostPopularModel;
import com.springbootcallingexternalapi.SpringBootCallingExternalApiApplication;
import java.sql.Timestamp;
import java.util.List;
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

@SpringBootTest(classes = MostPopularRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
class MostPopularRepositoryTest {

  @Autowired
  private final JdbcTemplate jdbcTemplate;
  @Autowired
  private MostPopularRepository repositoryMost;
  @Autowired
  private LeagueRepository repositoryLegue;
  @Autowired
  private final MasteryRepository repositoryMaster;

  @Autowired
  public MostPopularRepositoryTest(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    repositoryMaster = new MasteryRepository();
    ReflectionTestUtils.setField(repositoryMaster, "jdbcTemplate", jdbcTemplate);
  }

  @BeforeEach
  void setup() {
    jdbcTemplate.execute("TRUNCATE TABLE \"MasteryHistory\" RESTART IDENTITY CASCADE");
  }

  @BeforeEach
  void setup2() {
    jdbcTemplate.execute("TRUNCATE TABLE \"LeagueHistory\" RESTART IDENTITY CASCADE");
  }

  @Test
  void mostPopularDefaultCase() throws AccountDataException, NoDataException {
    LeagueInfoModel infoModel = new LeagueInfoModel(
        Timestamp.valueOf("2022-05-21 22:25:28.744"),
        "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
        "RANKED_SOLO_5x5",
        "PLATINUM",
        "I",
        "testuno",
        76,
        5476,
        "testuno"
    );
    LeagueInfoModel infoModel2 = new LeagueInfoModel(
        Timestamp.valueOf("2022-05-22 22:25:28.744"),
        "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
        "RANKED_SOLO_5x5",
        "PLATINUM",
        "I",
        "testuno",
        76,
        5476,
        "testuno"
    );
    LeagueInfoModel infoModel3 = new LeagueInfoModel(
        Timestamp.valueOf("2022-05-23 22:25:28.744"),
        "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
        "RANKED_SOLO_5x5",
        "PLATINUM",
        "I",
        "testuno",
        76,
        5476,
        "testuno"
    );
    LeagueInfoModel infoModel4 = new LeagueInfoModel(
        Timestamp.valueOf("2022-05-24 23:25:28.744"),
        "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
        "RANKED_SOLO_5x5",
        "PLATINUM",
        "I",
        "testdos",
        76,
        5476,
        "testdos"
    );
    LeagueInfoModel infoModel5 = new LeagueInfoModel(
        Timestamp.valueOf("2022-05-25 22:25:28.744"),
        "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
        "RANKED_SOLO_5x5",
        "PLATINUM",
        "I",
        "testdos",
        76,
        5476,
        "testdos"
    );
    MasteryHistoryInfoModel masteryModel = new MasteryHistoryInfoModel(
        112,
        7,
        250,
        Timestamp.valueOf("2022-05-24 22:25:28.744"),
        1
    );
    MasteryHistoryInfoModel masteryModel2 = new MasteryHistoryInfoModel(
        8,
        7,
        156000,
        Timestamp.valueOf("2022-05-25 22:25:28.744"),
        1
    );
    MasteryHistoryInfoModel masteryModel3 = new MasteryHistoryInfoModel(
        112,
        7,
        20000,
        Timestamp.valueOf("2022-05-26 22:25:28.744"),
        1
    );
    MasteryHistoryInfoModel masteryModel4 = new MasteryHistoryInfoModel(
        112,
        7,
        155000,
        Timestamp.valueOf("2022-05-27 22:25:28.744"),
        1
    );
    MasteryHistoryInfoModel masteryModel5 = new MasteryHistoryInfoModel(
        8,
        7,
        450000,
        Timestamp.valueOf("2022-05-28 22:25:28.744"),
        1
    );
    MostPopularModel expectedResult = new MostPopularModel(
        1,
        8,
        "May 21"
        //Timestamp.valueOf("2022-04-01 00:00:00")
    );
    repositoryLegue.insertLeagueInfo(infoModel, 1, 1);
    repositoryLegue.insertLeagueInfo(infoModel2, 1, 1);
    repositoryLegue.insertLeagueInfo(infoModel3, 1, 1);
    repositoryLegue.insertLeagueInfo(infoModel4, 2, 2);
    repositoryLegue.insertLeagueInfo(infoModel5, 2, 2);
    repositoryMaster.insertMasteryInfo(masteryModel);
    repositoryMaster.insertMasteryInfo(masteryModel2);
    repositoryMaster.insertMasteryInfo(masteryModel3);
    repositoryMaster.insertMasteryInfo(masteryModel4);
    repositoryMaster.insertMasteryInfo(masteryModel5);

    List<MostPopularModel> resultSet = repositoryMost.popularAccount();
    Assertions.assertEquals(1, resultSet.size());
    MostPopularModel result = resultSet.get(0);

    Assertions.assertEquals(expectedResult.getAccount(), result.getAccount());
    Assertions.assertEquals(expectedResult.getChampion(), result.getChampion());
    Assertions.assertEquals(expectedResult.getDate(), result.getDate());
  }

  @Test
  void mostPopularNoDataException() {

    Assertions.assertThrows(NoDataException.class, () -> repositoryMost.popularAccount());
  }
}