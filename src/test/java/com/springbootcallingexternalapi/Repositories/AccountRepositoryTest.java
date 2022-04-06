package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.AccountModel;
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

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes= AccountRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
public class AccountRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private AccountRepository repository;

    @Autowired
    public AccountRepositoryTest(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        repository = new AccountRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", jdbcTemplate);

    }

    @BeforeEach
    void setup(){
        jdbcTemplate.execute("TRUNCATE TABLE \"Accounts\"");
    }

    @Test
    void insertarExitosamenteCasoDefault() throws CharacterNotAllowedException, AccountDataException, OwnerNotAllowedException {
        //GIVEN A NORMAL ACCOUNT WITH ALL DATA AND A STANDARD OWNER FROM THE BASE OWNERS

        //Account with all data

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                4864,
                1648276400000L,
                109L
        );

        //Owner Standard
        String owner  = "kusi";

        AccountModel modelo = new AccountModel("IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                4864,
                1648276400000L,
                109,
                owner);


        //WHEN TRYING TO INSERT IT INTO THE DATABASE AND RETRIEVING THE RESULTS
        repository.insertAccount(baseModel, owner);

        //THEN INSERT PROPERLY AND PERSIST THE DATA IN THE DATABASE.
        List<AccountModel> resultSet = jdbcTemplate.query("SELECT * FROM \"Accounts\"", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(1, resultSet.size());
        AccountModel result = resultSet.get(0);

        Assertions.assertEquals(modelo.getAccountId(), result.getAccountId());
        Assertions.assertEquals(modelo.getId(), result.getId());
        Assertions.assertEquals(modelo.getName().toLowerCase(Locale.ROOT), result.getName());
        Assertions.assertEquals(modelo.getOwner().toLowerCase(Locale.ROOT), result.getOwner());
        Assertions.assertEquals(modelo.getPuuid(), result.getPuuid());
        Assertions.assertEquals(modelo.getSummonerLevel(), result.getSummonerLevel());
        Assertions.assertEquals(modelo.getRevisionDate(), result.getRevisionDate());
        Assertions.assertEquals(modelo.getProfileIconId(), result.getProfileIconId());
    }

    @Test
    void CharacterNotAllowedExceptionEnInsertarCuenta(){
        String owner = "ku<<<";
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                4864,
                1648276400000L,
                109L
        );
        Exception exception = assertThrows(CharacterNotAllowedException.class,() -> repository.insertAccount(baseModel, owner));

        String expectedMessage = " has characters not allowed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void AccountDataExceptionEnInsertarCuenta() throws CharacterNotAllowedException, AccountDataException, OwnerNotAllowedException {
        String owner = "kusi";
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                4864,
                1648276400000L,
                109L
        );
        repository.insertAccount(baseModel, owner);
        Exception exception = assertThrows(AccountDataException.class, () -> repository.insertAccount(baseModel, owner));

        String expectedMessage = " NO SON VALIDOS, POR FAVOR RECTIFICAR";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void OwnerNotAllowedExceptionEnInsertarCuenta(){
        String owner = "pepito";
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                4864,
                1648276400000L,
                109L
        );

        Exception exception = assertThrows(OwnerNotAllowedException.class, () -> repository.insertAccount(baseModel, owner));

        String expectedMessage = " is not allowed for this api";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void eliminarExitosamenteDeleteAccountCasoDefault() throws AccountOrOwnerNotFoundException, CharacterNotAllowedException, AccountDataException, OwnerNotAllowedException {


        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                4864,
                1648276400000L,
                109L
        );

        String owner = "kusi";

        repository.insertAccount(baseModel,owner);
        repository.deleteAccount(owner,baseModel.getName());

        List<AccountModel> resultSet = jdbcTemplate.query("SELECT * FROM \"Accounts\"", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(0,resultSet.size());
    }

    @Test
    void AccountOrOwnerNotFoundExceptionEnDeleteAccount (){
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                4864,
                1648276400000L,
                109L
        );
        String owner = "pepito";

        Exception exception = assertThrows(AccountOrOwnerNotFoundException.class, () -> repository.deleteAccount(baseModel.getName(), owner));

        String expectedMessage = " NO FUE ENCONTRADA, PORFAVOR RECTIFICAR.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void CharacterNotAllowedExceptionEnDeleteAccount (){

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "<<<",
                4864,
                1648276400000L,
                109L
        );
        String owner = "<<<<";

        Exception exception = assertThrows(CharacterNotAllowedException.class, () -> repository.deleteAccount(baseModel.getName(), owner));

        String expectedMessage = " has characters not allowed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}