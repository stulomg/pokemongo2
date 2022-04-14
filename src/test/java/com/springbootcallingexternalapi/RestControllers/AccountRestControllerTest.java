package com.springbootcallingexternalapi.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Repositories.AccountRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"Accounts\"");
    }

    @Test
    public void deleteExitosamenteCasoDefault() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Accounts\"");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "soyeon lover",
                4864,
                1648276400000L,
                109L
        );

        String owner = "kusi";
        accountRepository.insertAccount(baseModel, owner);

        MvcResult mvcResult = mockMvc.perform(delete("/account/delete/kusi/soyeon lover")).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals("Delete successfully", content);

        List<AccountModel> resultSet = jdbcTemplate.query("SELECT * FROM \"Accounts\"", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(0, resultSet.size());
    }

    @Test
    public void accountOrOwnerNotFoundExceptionEnDeleteAccount() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Accounts\"");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "soyeon lover",
                4864,
                1648276400000L,
                109L
        );

        String owner = "kusi";
        accountRepository.insertAccount(baseModel, owner);

        mockMvc.perform(delete("/account/delete/kusarin/soyeon lover")).andExpect(status().isNotFound()).andExpect(content().string("LA CUENTA: soyeon lover, VINCULADA AL USUARIO: kusarin NO FUE ENCONTRADA, PORFAVOR RECTIFICAR."));
    }

    @Test
    public void characterNotAllowedExceptionEnDeleteAccount() throws Exception {

        mockMvc.perform(delete("/account/delete/<<kusi/soyeon lover")).andExpect(status().isBadRequest()).andExpect(content().string("<<kusi or soyeon lover has characters not allowed"));
    }

    @Test
    public void retrieveAccountByOwnerExitosamenteCasoDefault() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Accounts\"");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                4864,
                1648276400000L,
                109L
        );

        String owner = ("Kusi").toLowerCase(Locale.ROOT);

        AccountModel modelo = new AccountModel("IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                4864,
                1648276400000L,
                109L,
                owner);

        accountRepository.insertAccount(baseModel, owner);

        mockMvc.perform(get("/account/find-by-owner/kusi")).andExpect(status().isOk()).andReturn();

        List<AccountModel> resultSet = jdbcTemplate.query("SELECT FROM \"Accounts\" WHERE LOWER (owner)='kusi'", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(1, resultSet.size());
    }

    @Test
    public void characterNotAllowedExceptionEnRetrieveAccountByOwner() throws Exception {

        mockMvc.perform(get("/account/find-by-owner/<<kusi")).andExpect(status().isBadRequest()).andExpect(content().string("<<kusi has characters not allowed"));
    }

    @Test
    public void ownerNotFoundExceptionEnRetrieveAccountByOwner() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Accounts\"");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "soyeon lover",
                4864,
                1648276400000L,
                109L
        );

        String owner = "kusi";
        accountRepository.insertAccount(baseModel, owner);

        mockMvc.perform(get("/account/find-by-owner/kusarin")).andExpect(status().isNotFound()).andExpect(content().string("EL OWNER kusarin NO FUE ENCONTRADO, POR FAVOR RECTIFICAR"));
    }

    @Test
    public void retrieveAccountByNameExitosamenteCasoDefault() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Accounts\"");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                ("Soyeon Lover").toLowerCase(Locale.ROOT),
                4864,
                1648276400000L,
                109L
        );

        String owner = "kusi";
        String account = baseModel.getName();

        AccountModel modelo = new AccountModel("IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                4864,
                1648276400000L,
                109L,
                owner);

        accountRepository.insertAccount(baseModel, owner);

        mockMvc.perform(get("/account/find-by-name/soyeon lover")).andExpect(status().isOk()).andReturn();

        List<AccountModel> resultSet = jdbcTemplate.query("SELECT FROM \"Accounts\" WHERE LOWER (name) ='soyeon lover'", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(1, resultSet.size());
    }

    @Test
    public void characterNotAllowedExceptionEnRetrieveAccountByName() throws Exception {

        mockMvc.perform(get("/account/find-by-name/soyeon<<<lover")).andExpect(status().isBadRequest()).andExpect(content().string("soyeon<<<lover has characters not allowed"));
    }

    @Test
    public void accountNotFoundExceptionEnRetrieveAccountByName() throws Exception {

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "soyeon lover",
                4864,
                1648276400000L,
                109L
        );

        String owner = "kusi";
        accountRepository.insertAccount(baseModel, owner);

        mockMvc.perform(get("/account/find-by-name/stulinpinguin")).andExpect(status().isNotFound()).andExpect(content().string("LA CUENTA stulinpinguin NO FUE ENCONTRADA, POR FAVOR RECTIFICAR"));
    }
}

