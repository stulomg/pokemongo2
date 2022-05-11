package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountExistsException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountModel;
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


@SpringBootTest(classes = AccountRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)

public class AccountRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private AccountRepository repository;
    private  OwnerRepository ownerRepository;

    @Autowired
    public AccountRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        repository = new AccountRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", jdbcTemplate);

    }

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"Account\"");
    }

    @Test
    void insertarExitosamenteCasoDefault() throws CharacterNotAllowedException, AccountDataException, OwnerNotAllowedException, AccountExistsException {
        //GIVEN A NORMAL ACCOUNT WITH ALL DATA AND A STANDARD OWNER FROM THE BASE OWNERS

        //Account with all data

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L);

        //Owner Standard
        Integer owner = 1;

        AccountModel modelo = new AccountModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L,
                owner);


        //WHEN TRYING TO INSERT IT INTO THE DATABASE AND RETRIEVING THE RESULTS
        repository.insertAccount(baseModel, owner);

        //THEN INSERT PROPERLY AND PERSIST THE DATA IN THE DATABASE.
        List<AccountModel> resultSet = jdbcTemplate.query("SELECT * FROM \"Account\"", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(1, resultSet.size());
        AccountModel result = resultSet.get(0);

        Assertions.assertEquals(modelo.getAccountId(), result.getAccountId());
        Assertions.assertEquals(modelo.getId(), result.getId());
        Assertions.assertEquals(modelo.getName().toLowerCase(Locale.ROOT), result.getName());
        Assertions.assertEquals(modelo.getOwner(), result.getOwner());
        Assertions.assertEquals(modelo.getPuuid(), result.getPuuid());
        Assertions.assertEquals(modelo.getRevisionDate(), result.getRevisionDate());
    }

    @Test
    void characterNotAllowedExceptionEnInsertarCuenta() {
        Integer owner = 5;
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L
        );
        Exception exception = assertThrows(CharacterNotAllowedException.class, () -> repository.insertAccount(baseModel, owner));

        String expectedMessage = " has characters not allowed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void accountDataExceptionEnInsertarCuenta() throws CharacterNotAllowedException, AccountDataException, OwnerNotAllowedException, AccountExistsException {
        Integer owner = 1;
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L
        );
        repository.insertAccount(baseModel, owner);
        Exception exception = assertThrows(AccountDataException.class, () -> repository.insertAccount(baseModel, owner));

        String expectedMessage = " NO SON VALIDOS, POR FAVOR RECTIFICAR";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ownerNotAllowedExceptionEnInsertarCuenta() {
        Integer owner = 100;
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L
        );

        Exception exception = assertThrows(OwnerNotAllowedException.class, () -> repository.insertAccount(baseModel, owner));

        String expectedMessage = " is not allowed for this api";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void updateExitosoCasoDefault() throws CharacterNotAllowedException, AccountNotFoundException, AccountDataException, OwnerNotAllowedException, AccountExistsException {
        //given
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L
        );
        Integer owner = 1;

        AccountModel model = new AccountModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "STULMEMITO",
                "F46S5D4F",
                "stulesunmeme",
                1324654564L,
                owner);

        repository.insertAccount(baseModel, owner);
        //When
        repository.accountUpdate(model,owner);
        List<AccountModel> resultSet = jdbcTemplate.query("SELECT * FROM \"Account\"", BeanPropertyRowMapper.newInstance(AccountModel.class));
        //Then
        Assertions.assertEquals(1, resultSet.size());
        Assertions.assertEquals(baseModel.getId(), resultSet.get(0).getId());
        Assertions.assertEquals(model.getAccountId(), resultSet.get(0).getAccountId());
        Assertions.assertEquals(model.getPuuid(), resultSet.get(0).getPuuid());
        Assertions.assertEquals(model.getName(), resultSet.get(0).getName());
        Assertions.assertEquals(model.getRevisionDate(), resultSet.get(0).getRevisionDate());
        Assertions.assertEquals(owner, resultSet.get(0).getOwner());
    }

    @Test
    void accountNotFoundExceptionEnAccountUpdate() {
        Integer owner = 1;

        AccountModel model = new AccountModel(
                "",
                "STULMEMITO",
                "F46S5D4F",
                "stulesunmeme",
                1324654564L,
                owner);

        Assertions.assertThrows(AccountNotFoundException.class, () -> repository.accountUpdate(model,owner));
    }

    @Test
    void characterNotAllowedAccountUpdate() {
        Integer owner = 1;

        AccountModel model = new AccountModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "STULMEMITO",
                "F46S5D4F",
                "stule*sunmeme",
                1324654564L,
                owner);

        Assertions.assertThrows(CharacterNotAllowedException.class, () -> repository.accountUpdate(model,owner));
    }

    @Test
    void eliminarExitosamenteDeleteAccountCasoDefault() throws AccountOrOwnerNotFoundException, CharacterNotAllowedException, AccountDataException, OwnerNotAllowedException, AccountExistsException {

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L
        );
        Integer owner = 1;
        String owner2 = "kusi";

        repository.insertAccount(baseModel, owner);
        repository.deleteAccount(baseModel.getName(),owner2,owner);

        List<AccountModel> resultSet = jdbcTemplate.query("SELECT * FROM \"Account\"", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(0, resultSet.size());
    }

    @Test
    void accountOrOwnerNotFoundExceptionEnDeleteAccount() throws CharacterNotAllowedException, OwnerNotFoundException {
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L
        );
        String owner = "pepito";
        Integer ownerID = Math.toIntExact(ownerRepository.retrieveOwnerIdByOwnerName(owner.toLowerCase(Locale.ROOT)));

        Exception exception = assertThrows(AccountOrOwnerNotFoundException.class, () -> repository.deleteAccount(baseModel.getName(), owner, ownerID));

        String expectedMessage = " NO FUE ENCONTRADA, PORFAVOR RECTIFICAR.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void characterNotAllowedExceptionEnDeleteAccount() throws CharacterNotAllowedException, OwnerNotFoundException {

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "<<<",
                1648276400000L
        );
        String owner = "<<<<";
        Integer ownerID = Math.toIntExact(ownerRepository.retrieveOwnerIdByOwnerName(owner.toLowerCase(Locale.ROOT)));
        Exception exception = assertThrows(CharacterNotAllowedException.class, () -> repository.deleteAccount(baseModel.getName(), owner,ownerID));

        String expectedMessage = " has characters not allowed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void retrieveAccountByOwnerExitosamenteCasoDefault() throws CharacterNotAllowedException, AccountDataException, OwnerNotAllowedException, OwnerNotFoundException, AccountExistsException {

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L
        );
        Integer owner =1;
        String owner2 = ("Kusi").toLowerCase(Locale.ROOT);

        AccountModel modelo = new AccountModel("IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L,
                owner);

        repository.insertAccount(baseModel, owner);
        Integer ownerID = Math.toIntExact(ownerRepository.retrieveOwnerIdByOwnerName(owner2.toLowerCase(Locale.ROOT)));
        List<AccountModel> resultSet = repository.retrieveAccountByOwner(owner2,ownerID);
        Assertions.assertEquals(1, resultSet.size());
        AccountModel result = resultSet.get(0);

        Assertions.assertEquals(modelo.getAccountId(), result.getAccountId());
        Assertions.assertEquals(modelo.getId(), result.getId());
        Assertions.assertEquals(modelo.getName().toLowerCase(Locale.ROOT), result.getName());
        Assertions.assertEquals(modelo.getOwner(), result.getOwner());
        Assertions.assertEquals(modelo.getPuuid(), result.getPuuid());
        Assertions.assertEquals(modelo.getRevisionDate(), result.getRevisionDate());
    }

    @Test
    void characterNotAllowedExceptionEnRetrieveAccountByOwner() {


        String owner = "<<>>";

        Integer ownerID = 100;
        Exception exception = assertThrows(CharacterNotAllowedException.class, () -> repository.retrieveAccountByOwner(owner,ownerID));

        String expectedMessage = " has characters not allowed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void ownerNotFoundExceptionEnRetrieveAccountByOwner() {

        String owner = "pepito";
        Integer ownerID = 100;

        Exception exception = assertThrows(OwnerNotFoundException.class, () -> repository.retrieveAccountByOwner(owner,ownerID));

        String expectedMessage = " NO FUE ENCONTRADO, POR FAVOR RECTIFICAR";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void retrieveAccountByAccountNameExitosamenteCasoDefault() throws CharacterNotAllowedException, AccountDataException, OwnerNotAllowedException, AccountNotFoundException, AccountExistsException {

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                ("Soyeon Lover").toLowerCase(Locale.ROOT),
                1648276400000L
        );

        Integer owner = 1;

        AccountModel modelo = new AccountModel("IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L,
                owner);

        repository.insertAccount(baseModel, owner);

        List<AccountModel> resultSet = repository.retrieveAccountByAccountName(baseModel.getName());
        Assertions.assertEquals(1, resultSet.size());
        AccountModel result = resultSet.get(0);

        Assertions.assertEquals(modelo.getAccountId(), result.getAccountId());
        Assertions.assertEquals(modelo.getId(), result.getId());
        Assertions.assertEquals(modelo.getName().toLowerCase(Locale.ROOT), result.getName());
        Assertions.assertEquals(modelo.getOwner(), result.getOwner());
        Assertions.assertEquals(modelo.getPuuid(), result.getPuuid());
        Assertions.assertEquals(modelo.getRevisionDate(), result.getRevisionDate());
    }

    @Test
    void characterNotAllowedEnRetrieveAccountByAccountName() {

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "<<<<",
                1648276400000L
        );

        Exception exception = assertThrows(CharacterNotAllowedException.class, () -> repository.retrieveAccountByAccountName(baseModel.getName()));

        String expectedMessage = " has characters not allowed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void accountNotFoundExceptionEnRetrieveAccountByAccountName() {

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "pepito pro",
                1648276400000L
        );

        Exception exception = assertThrows(AccountNotFoundException.class, () -> repository.retrieveAccountByAccountName(baseModel.getName()));

        String expectedMessage = " NO FUE ENCONTRADA, POR FAVOR RECTIFICAR";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}