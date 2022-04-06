package com.springbootcallingexternalapi.Repositories;


import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.Models.MasteryInfoModel;
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

@SpringBootTest (classes = MasteryRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration (classes = SpringBootCallingExternalApiApplication.class)
public class MasteryRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MasteryRepository repository;

    @Autowired
    public MasteryRepositoryTest(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        repository = new MasteryRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", jdbcTemplate);
    }

    @BeforeEach
    void setup (){jdbcTemplate.execute("TRUNCATE TABLE \"AccountMasteryHistory\"");}

    @Test
    void insertMasteryInfoExitosamenteCasoDefault() throws AccountDataException {

        MasteryHistoryInfoModel basemodel = new MasteryHistoryInfoModel(

                "ezreal",
                81L,
                7,
                561220,
                Timestamp.valueOf("2007-09-23 10:10:10.0"),
                "stul"
        );

        MasteryHistoryInfoModel modelo = new MasteryHistoryInfoModel(
                "ezreal",
                81L,
                7,
                561220,
                Timestamp.valueOf("2007-09-23 10:10:10.0"),
                "stul"
        );

        repository.insertMasteryInfo(basemodel);
        List<MasteryHistoryInfoModel> resultSet = jdbcTemplate.query("SELECT * FROM \"AccountMasteryHistory\"", BeanPropertyRowMapper.newInstance(MasteryHistoryInfoModel.class));
        Assertions.assertEquals(1, resultSet.size());
        MasteryHistoryInfoModel result = resultSet.get(0);

        Assertions.assertEquals(modelo.getChampionName(), result.getChampionName());
        Assertions.assertEquals(modelo.getChampionId(), result.getChampionId());
        Assertions.assertEquals(modelo.getChampionLevel(), result.getChampionLevel());
        Assertions.assertEquals(modelo.getChampionPoints(), result.getChampionPoints());
        Assertions.assertEquals(modelo.getAccount(), result.getAccount());
    }

    @Test
    void AccountDataExceptionEnInsertMasteryHistory (){

        MasteryHistoryInfoModel basemodel = new MasteryHistoryInfoModel(

                "ezreal",
                81L,
                7,
                561220,
                Timestamp.valueOf("2007-09-23 10:10:10.0"),
                "stul"
        );
        Exception exception = assertThrows(AccountDataException.class,() -> repository.insertMasteryInfo(basemodel));

        String expectedMessage = "LOS DATOS INGRESADOS PARA LA CUENTA ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void TraerExitosamenteAccounMasteryHistoryCasoDefault () throws AccountDataException, CharacterNotAllowedException, AccountNotFoundException {

        MasteryHistoryInfoModel basemodel = new MasteryHistoryInfoModel(

                "ezreal",
                81L,
                7,
                561220,
                Timestamp.valueOf("2007-09-23 10:10:10.0"),
                "stul"
        );
        MasteryHistoryInfoModel basemodel2 = new MasteryHistoryInfoModel(
                "teemo",
                17L,
                5,
                41429,
                Timestamp.valueOf("2007-09-23 10:11:10.0"),
                "stul"
        );


        repository.insertMasteryInfo(basemodel);
        repository.insertMasteryInfo(basemodel2);


        List<MasteryHistoryInfoModel> resultSet = repository.AccountMasteryHistory(basemodel.getAccount());
        Assertions.assertEquals(2, resultSet.size());
        MasteryHistoryInfoModel result = resultSet.get(0);

        Assertions.assertEquals(basemodel.getChampionName(), result.getChampionName());
        Assertions.assertEquals(basemodel.getChampionId(), result.getChampionId());
        Assertions.assertEquals(basemodel.getChampionLevel(), result.getChampionLevel());
        Assertions.assertEquals(basemodel.getChampionPoints(), result.getChampionPoints());
        Assertions.assertEquals(basemodel.getAccount(), result.getAccount());
    }

    @Test
    void AccountNotFoundExceptionEnAccountMasteryHistory () throws AccountDataException {

        MasteryHistoryInfoModel basemodel = new MasteryHistoryInfoModel(

                "ezreal",
                81L,
                7,
                561220,
                Timestamp.valueOf("2007-09-23 10:10:10.0"),
                "stul"
        );
        repository.insertMasteryInfo(basemodel);
        String account = "pepito";

        Exception exception = assertThrows(AccountNotFoundException.class,() -> repository.AccountMasteryHistory(account));

        String expectedMessage = " NO FUE ENCONTRADA, POR FAVOR RECTIFICAR";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void CharacterNotAllowedEnAccountMasteryHistory () throws AccountDataException {

        MasteryHistoryInfoModel basemodel = new MasteryHistoryInfoModel(

                "ezreal",
                81L,
                7,
                561220,
                Timestamp.valueOf("2007-09-23 10:10:10.0"),
                "stul"
        );
        repository.insertMasteryInfo(basemodel);
        String account ="<<<";

        Exception exception = assertThrows(CharacterNotAllowedException.class,() -> repository.AccountMasteryHistory(account));

        String expectedMessage = " has characters not allowed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
