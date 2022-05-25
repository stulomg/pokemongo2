package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountBaseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.AccountModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Services.SecurityUserService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Locale;

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
    @Autowired
    private SecurityUserService securityUserService;
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE;");
        objectMapper = new ObjectMapper();
    }

    @Test
    public void deleteSuccessfullyDefaultCase() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE;");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "soyeon lover",
                1648276400000L
        );

        Integer owner = 1;
        accountRepository.insertAccount(baseModel, owner);

        String token = securityUserService.generateToken();
        MvcResult mvcResult = mockMvc.perform(delete("/account/delete/soyeon lover").header("authorization", token)).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals("Delete successfully", content);

        List<AccountModel> resultSet = jdbcTemplate.query("SELECT * FROM \"Account\"", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(0, resultSet.size());
    }

    @Test
    public void AccountNotFoundExceptionDeleteAccount() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE;");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "soyeon lover",
                1648276400000L
        );

        Integer owner = 1;
        accountRepository.insertAccount(baseModel, owner);
        String token = securityUserService.generateToken();
        mockMvc.perform(delete("/account/delete/soyeonlover").header("authorization", token)).andExpect(status().isNotFound()).andExpect(content().string("The account soyeonlover was not found, please rectify"));
    }

    @Test
    public void characterNotAllowedExceptionDeleteAccount() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(delete("/account/delete/soyeon*lover").header("authorization", token)).andExpect(status().isBadRequest()).andExpect(content().string("soyeon*lover has characters not allowed"));
    }

    @Test
    public void retrieveAccountByOwnerSuccessfullyDefaultCase() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE;");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L
        );

        Integer owner = 1;

        AccountModel modelo = new AccountModel("IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L,
                owner,
                "ownerName");

        accountRepository.insertAccount(baseModel, owner);
        String token = securityUserService.generateToken();
        mockMvc.perform(get("/account/find-by-owner/testuno").header("authorization", token)).andExpect(status().isOk()).andReturn();

        List<AccountModel> resultSet = jdbcTemplate.query("SELECT FROM \"Account\" WHERE owner =1", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(1, resultSet.size());
    }

    @Test
    public void characterNotAllowedExceptionRetrieveAccountByOwner() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(get("/account/find-by-owner/<<kusi").header("authorization", token)).andExpect(status().isBadRequest()).andExpect(content().string("<<kusi has characters not allowed"));
    }

    @Test
    public void ownerNotFoundExceptionRetrieveAccountByOwner() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE;");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "soyeon lover",
                1648276400000L
        );

        Integer owner = 1;
        accountRepository.insertAccount(baseModel, owner);
        String token = securityUserService.generateToken();
        mockMvc.perform(get("/account/find-by-owner/kusarin").header("authorization", token)).andExpect(status().isNotFound()).andExpect(content().string("The owner kusarin was not found, please rectify"));
    }

    @Test
    public void retrieveAccountByNameSuccessfullyDefaultCase() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE;");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                ("Soyeon Lover").toLowerCase(Locale.ROOT),
                1648276400000L
        );

        Integer owner = 1;
        String account = baseModel.getName();

        AccountModel modelo = new AccountModel("IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "Soyeon Lover",
                1648276400000L,
                owner,
                "ownerName");

        accountRepository.insertAccount(baseModel, owner);
        String token = securityUserService.generateToken();
        mockMvc.perform(get("/account/find-by-name/soyeon lover").header("authorization", token)).andExpect(status().isOk()).andReturn();

        List<AccountModel> resultSet = jdbcTemplate.query("SELECT FROM \"Account\" WHERE LOWER (name) ='soyeon lover'", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(1, resultSet.size());
    }

    @Test
    public void characterNotAllowedExceptionRetrieveAccountByName() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(get("/account/find-by-name/soyeon<<<lover").header("authorization", token)).andExpect(status().isBadRequest()).andExpect(content().string("soyeon<<<lover has characters not allowed"));
    }

    @Test
    public void accountNotFoundExceptionRetrieveAccountByName() throws Exception {

        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE;");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "soyeon lover",
                1648276400000L
        );

        Integer owner = 1;

        accountRepository.insertAccount(baseModel, owner);
        String token = securityUserService.generateToken();
        mockMvc.perform(get("/account/find-by-name/stulinpinguin").header("authorization", token)).andExpect(status().isNotFound()).andExpect(content().string("The account stulinpinguin was not found, please rectify"));
    }

    @Test
    public void accountUpdate() throws Exception {

        objectMapper = new ObjectMapper();

        AccountBaseModel baseModel = new AccountBaseModel(
                "qwx-QX5fD0_freIh9Wai_5nVkDrZ2urz_VTfA74M9e9P",
                "nS4rwFEX4a58v9ghLVldu34nNV4_GVPLNnDJiRiLZLxG0x4",
                "dkiVwTUbuZMVmqV0T6-KIDOGTrBeeqoJhR5It3ksJ3j1UwM2Dmk1rm2NVcjyffiF-hHBtXRpnkjXAw",
                "Darkclaw",
                1653003765000L
        );

        Integer owner = 1;

        AccountModel model = new AccountModel(
                "qwx-QX5fD0_freIh9Wai_5nVkDrZ2urz_VTfA74M9e9P",
                "nS4rwFEX4a58v9ghLVldu34nNV4_GVPLNnDJiRiLZLxG0x",
                "dkiVwTUbuZMVmqV0T6-KIDOGTrBeeqoJhR5It3ksJ3j1UwM2Dmk1rm2NVcjyffiF-hHBtXRpnkjXAw",
                "Darkclawx",
                1653003765000L,
                owner,
                "testuno");

        accountRepository.insertAccount(baseModel, owner);

        String token = securityUserService.generateToken();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/account/update").header("authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(model))).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals("Updated successfully", content);

    }

    @Test
    public void accountUpdateAccountNotFoundException() throws Exception {
        objectMapper = new ObjectMapper();

        Integer owner = 1;

        AccountModel model = new AccountModel(
                "qwx-QX5fD0_freIh9Wai_5nVkDrZ2urz_VTfA74M9e9P",
                "nS4rwFEX4a58v9ghLVldu34nNV4_GVPLNnDJiRiLZLxG0x",
                "dkiVwTUbuZMVmqV0T6-KIDOGTrBeeqoJhR5It3ksJ3j1UwM2Dmk1rm2NVcjyffiF-hHBtXRpnkjXAw",
                "Darkclawx",
                1653003765000L,
                owner,
                "testuno");

        String token = securityUserService.generateToken();
        mockMvc.perform(put("/account/update").header("authorization", token).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(model))).andExpect(status().isNotFound()).andExpect(content().string("The account Darkclawx was not found, please rectify"));
    }

    @Test
    public void accountUpdateOwnerNotFoundException() throws Exception {
        objectMapper = new ObjectMapper();

        Integer owner = 1;

        AccountModel model = new AccountModel(
                "qwx-QX5fD0_freIh9Wai_5nVkDrZ2urz_VTfA74M9e9P",
                "nS4rwFEX4a58v9ghLVldu34nNV4_GVPLNnDJiRiLZLxG0x",
                "dkiVwTUbuZMVmqV0T6-KIDOGTrBeeqoJhR5It3ksJ3j1UwM2Dmk1rm2NVcjyffiF-hHBtXRpnkjXAw",
                "Darkclawx",
                1653003765000L,
                owner,
                "tesuno");

        String token = securityUserService.generateToken();
        mockMvc.perform(put("/account/update").header("authorization", token).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(model))).andExpect(status().isNotFound()).andExpect(content().string("The owner tesuno was not found, please rectify"));
    }

    @Test
    public void accountUpdateCharacterNotAllowedException() throws Exception {
        objectMapper = new ObjectMapper();

        Integer owner = 1;

        AccountModel model = new AccountModel(
                "qwx-QX5fD0_freIh9Wai_5nVkDrZ2urz_VTfA74M9e9P",
                "nS4rwFEX4a58v9ghLVldu34nNV4_GVPLNnDJiRiLZLxG0x",
                "dkiVwTUbuZMVmqV0T6-KIDOGTrBeeqoJhR5It3ksJ3j1UwM2Dmk1rm2NVcjyffiF-hHBtXRpnkjXAw",
                "D*arkclawx",
                1653003765000L,
                owner,
                "testuno");

        String token = securityUserService.generateToken();
        mockMvc.perform(put("/account/update").header("authorization", token).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(model))).andExpect(status().isBadRequest()).andExpect(content().string("D*arkclawx has characters not allowed"));
    }
}
