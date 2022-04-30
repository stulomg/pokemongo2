package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MostPopularModel;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = MostPopularRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)

class MostPopularRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MostPopularRepository repositoryMost;
    @Autowired
    private LeagueRepository repositoryLegue;
    @Autowired
    private MasteryRepository repositoryMaster;


    @Autowired
    public MostPopularRepositoryTest(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        repositoryMaster = new MasteryRepository();
        ReflectionTestUtils.setField(repositoryMaster, "jdbcTemplate", jdbcTemplate);
    }
    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"AccountMasteryHistory\"");
    }
    @BeforeEach
    void setup2() {
        jdbcTemplate.execute("TRUNCATE TABLE \"LeagueInfo\"");
    }

    @Test
    void mostPopularCaseDefault() throws AccountDataException, CharacterNotAllowedException, NoDataException {
        LeagueInfoModel infoModel = new LeagueInfoModel(
                Timestamp.valueOf("2022-04-21 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "vantiax",
                76,
                5476,
                "stul"
        );
        LeagueInfoModel infoModel2 = new LeagueInfoModel(
                Timestamp.valueOf("2022-04-22 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "darkclaw",
                76,
                5476,
                "stul"
        );
        LeagueInfoModel infoModel3 = new LeagueInfoModel(
                Timestamp.valueOf("2022-04-23 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "darkclaw",
                76,
                5476,
                "stul"
        );
        LeagueInfoModel infoModel4 = new LeagueInfoModel(
                Timestamp.valueOf("2022-04-21 23:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "vantiax",
                76,
                5476,
                "kusi"
        );
        LeagueInfoModel infoModel5 = new LeagueInfoModel(
                Timestamp.valueOf("2022-04-24 22:25:28.744"),
                "ba78b27d-a3a9-45fd-9b38-4bdb587dd45a",
                "RANKED_SOLO_5x5",
                "PLATINUM",
                "I",
                "vantiax",
                76,
                5476,
                "kusi"
        );
        MasteryHistoryInfoModel masteryModel = new MasteryHistoryInfoModel(
                "viktor",
                81L,
                7,
                250,
                Timestamp.valueOf("2022-04-24 22:25:28.744"),
                "vantiax"
        );
        MasteryHistoryInfoModel masteryModel2 = new MasteryHistoryInfoModel(
                "vladimir",
                81L,
                7,
                156000,
                Timestamp.valueOf("2022-04-25 22:25:28.744"),
                "vantiax"
        );
        MasteryHistoryInfoModel masteryModel3 = new MasteryHistoryInfoModel(
                "viktor",
                81L,
                7,
                20000,
                Timestamp.valueOf("2022-04-26 22:25:28.744"),
                "vantiax"
        );
        MasteryHistoryInfoModel masteryModel4 = new MasteryHistoryInfoModel(
                "viktor",
                81L,
                7,
                155000,
                Timestamp.valueOf("2022-04-27 22:25:28.744"),
                "vantiax"
        );
        MasteryHistoryInfoModel masteryModel5 = new MasteryHistoryInfoModel(
                "vladimir",
                81L,
                7,
                450000,
                Timestamp.valueOf("2022-04-28 22:25:28.744"),
                "vantiax"
        );
        MostPopularModel espectedResult = new MostPopularModel(
                "vantiax",
                "vladimir",
                "Apr 21"
                //Timestamp.valueOf("2022-04-01 00:00:00")
        );

        repositoryLegue.insertLeagueInfo(infoModel, infoModel.getOwner());
        repositoryLegue.insertLeagueInfo(infoModel2, infoModel2.getOwner());
        repositoryLegue.insertLeagueInfo(infoModel3, infoModel3.getOwner());
        repositoryLegue.insertLeagueInfo(infoModel4, infoModel4.getOwner());
        repositoryLegue.insertLeagueInfo(infoModel5, infoModel5.getOwner());
        repositoryMaster.insertMasteryInfo(masteryModel);
        repositoryMaster.insertMasteryInfo(masteryModel2);
        repositoryMaster.insertMasteryInfo(masteryModel3);
        repositoryMaster.insertMasteryInfo(masteryModel4);
        repositoryMaster.insertMasteryInfo(masteryModel5);

        List<MostPopularModel> resultSet = repositoryMost.popularAccount();
        Assertions.assertEquals(1, resultSet.size());
        MostPopularModel result = resultSet.get(0);

        Assertions.assertEquals(espectedResult.getAccount(), result.getAccount());
        Assertions.assertEquals(espectedResult.getChampionName(), result.getChampionName());
        Assertions.assertEquals(espectedResult.getDate(), result.getDate());
    }

    @Test
    void NoDataException() {

        Assertions.assertThrows(NoDataException.class, () -> repositoryMost.popularAccount());
    }



}