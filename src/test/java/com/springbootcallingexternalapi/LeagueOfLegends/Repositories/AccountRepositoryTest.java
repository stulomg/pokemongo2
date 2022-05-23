package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountExistsOrNotException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
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
    @Autowired
    private AccountRepository repository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    public AccountRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        repository = new AccountRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", jdbcTemplate);

    }

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE;");
    }

    @Test
    void insertSuccessfullyDefaultCase() throws AccountDataException, AccountExistsOrNotException {
        //GIVEN A NORMAL ACCOUNT WITH ALL DATA AND A STANDARD OWNER FROM THE BASE OWNER

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
    void AccountExistsExceptionInsertAccount() throws AccountDataException, AccountExistsOrNotException {
        Integer owner = 1;
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L
        );
        repository.insertAccount(baseModel, owner);
        Exception exception = assertThrows(AccountExistsOrNotException.class, () -> repository.insertAccount(baseModel, owner));

        String expectedMessage = "Account already registered";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateSuccessfullyDefaultCase() throws CharacterNotAllowedException, AccountNotFoundException, AccountDataException, AccountExistsOrNotException {
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
        repository.accountUpdate(model, owner);
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
    void accountNotFoundExceptionAccountUpdate() {
        Integer owner = 1;

        AccountModel model = new AccountModel(
                "",
                "STULMEMITO",
                "F46S5D4F",
                "stulesunmeme",
                1324654564L,
                owner);
        Assertions.assertThrows(AccountNotFoundException.class, () -> repository.accountUpdate(model, owner));
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
        Assertions.assertThrows(CharacterNotAllowedException.class, () -> repository.accountUpdate(model, owner));
    }

    @Test
    void DeleteAccountDefaultCase() throws CharacterNotAllowedException, AccountDataException, AccountExistsOrNotException, AccountNotFoundException {

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
        repository.deleteAccount(baseModel.getName(), owner);

        List<AccountModel> resultSet = jdbcTemplate.query("SELECT * FROM \"Account\"", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(0, resultSet.size());
    }

    @Test
    void AccountNotFoundExceptionDeleteAccount() throws CharacterNotAllowedException, OwnerNotFoundException {
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L
        );
        Integer owner = 1;

        Exception exception = assertThrows(AccountNotFoundException.class, () -> repository.deleteAccount(baseModel.getName(), owner));

        String expectedMessage = "LA CUENTA Soyeon Lover NO FUE ENCONTRADA, POR FAVOR RECTIFICAR";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void characterNotAllowedExceptionDeleteAccount() throws CharacterNotAllowedException, OwnerNotFoundException {

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "<<<",
                1648276400000L
        );
        Integer owner = 1;
        Exception exception = assertThrows(CharacterNotAllowedException.class, () -> repository.deleteAccount(baseModel.getName(), owner));

        String expectedMessage = " has characters not allowed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void retrieveAccountByOwnerSuccessfullyDefaultCase() throws CharacterNotAllowedException, AccountDataException, OwnerNotAllowedException, OwnerNotFoundException, AccountExistsOrNotException {

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L
        );
        Integer ownerID = 1;
        String owner = ("testuno").toLowerCase(Locale.ROOT);

        AccountModel modelo = new AccountModel("IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L,
                owner);

        repository.insertAccount(baseModel, ownerID);
        List<AccountModel> resultSet = repository.retrieveAccountByOwner(owner, ownerID);
        Assertions.assertEquals(1, resultSet.size());
        AccountModel result = resultSet.get(0);

        Assertions.assertEquals(modelo.getAccountId(), result.getAccountId());
        Assertions.assertEquals(modelo.getId(), result.getId());
        Assertions.assertEquals(modelo.getName().toLowerCase(Locale.ROOT), result.getName());
        Assertions.assertEquals(ownerID, result.getOwner());
        Assertions.assertEquals(modelo.getPuuid(), result.getPuuid());
        Assertions.assertEquals(modelo.getRevisionDate(), result.getRevisionDate());
    }

    @Test
    void characterNotAllowedExceptionRetrieveAccountByOwner() {

        String owner = "<<>>";

        Integer ownerID = 1;
        Exception exception = assertThrows(CharacterNotAllowedException.class, () -> repository.retrieveAccountByOwner(owner, ownerID));

        String expectedMessage = " has characters not allowed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void ownerNotFoundExceptionRetrieveAccountByOwner() {
        String owner = "pepito";
        Integer ownerID = 100;
        Exception exception = assertThrows(OwnerNotFoundException.class, () -> repository.retrieveAccountByOwner(owner, ownerID));
        String expectedMessage = " NO FUE ENCONTRADO, POR FAVOR RECTIFICAR";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void retrieveAccountByAccountNameSuccessfullyDefaultCase() throws CharacterNotAllowedException, AccountDataException, AccountNotFoundException, AccountExistsOrNotException {
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
    void characterNotAllowedRetrieveAccountByAccountName() {

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
    void accountNotFoundExceptionRetrieveAccountByAccountName() {

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

    @Test
    void retrieveOwnerIdByAccount() throws AccountDataException, AccountExistsOrNotException, CharacterNotAllowedException, AccountNotFoundException {
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                ("Soyeon Lover").toLowerCase(Locale.ROOT),
                1648276400000L
        );
        Integer owner = 1;
        Long OwnerSpected = 1L;
        repository.insertAccount(baseModel, owner);
        Long resultSet = repository.retrieveOwnerIdByAccount(baseModel.getName());
        Assertions.assertEquals(OwnerSpected, resultSet);
    }
    @Test
    void AccountNotFoundExceptionRetrieveOwnerIdByAccount(){
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                ("test").toLowerCase(Locale.ROOT),
                1648276400000L
        );
        Exception exception = assertThrows(AccountNotFoundException.class, () -> repository.retrieveOwnerIdByAccount(baseModel.getName()));
        String expectedMessage = "The account test was not found, please rectify";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void CharacterNotAllowedExceptionRetrieveOwnerIdByAccount() {
        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                ("test*").toLowerCase(Locale.ROOT),
                1648276400000L
        );
        Exception exception = assertThrows(CharacterNotAllowedException.class, () -> repository.retrieveOwnerIdByAccount(baseModel.getName()));
        String expectedMessage = "test* has characters not allowed";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}