package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.criteria.CriteriaBuilder;
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


    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"Account\" RESTART IDENTITY CASCADE;");
    }

    @Test
    public void deleteExitosamenteCasoDefault() throws Exception {

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
    public void AccountExistsOrNotExceptionEnDeleteAccount() throws Exception {

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
        mockMvc.perform(delete("/account/delete/soyeonlover").header("authorization", token)).andExpect(status().isNotFound()).andExpect(content().string("LA CUENTA soyeonlover NO FUE ENCONTRADA, POR FAVOR RECTIFICAR"));
    }

    @Test
    public void characterNotAllowedExceptionEnDeleteAccount() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(delete("/account/delete/soyeon*lover").header("authorization", token)).andExpect(status().isBadRequest()).andExpect(content().string("soyeon*lover has characters not allowed"));
    }

    @Test
    public void retrieveAccountByOwnerExitosamenteCasoDefault() throws Exception {

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
                owner);

        accountRepository.insertAccount(baseModel, owner);
        String token = securityUserService.generateToken();
        mockMvc.perform(get("/account/find-by-owner/testuno").header("authorization", token)).andExpect(status().isOk()).andReturn();

        List<AccountModel> resultSet = jdbcTemplate.query("SELECT FROM \"Account\" WHERE owner =1", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(1, resultSet.size());
    }

    @Test
    public void characterNotAllowedExceptionEnRetrieveAccountByOwner() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(get("/account/find-by-owner/<<kusi").header("authorization", token)).andExpect(status().isBadRequest()).andExpect(content().string("<<kusi has characters not allowed"));
    }

    @Test
    public void ownerNotFoundExceptionEnRetrieveAccountByOwner() throws Exception {

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
        mockMvc.perform(get("/account/find-by-owner/kusarin").header("authorization", token)).andExpect(status().isNotFound()).andExpect(content().string("EL OWNER kusarin NO FUE ENCONTRADO, POR FAVOR RECTIFICAR"));
    }

    @Test
    public void retrieveAccountByNameExitosamenteCasoDefault() throws Exception {

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
                owner);

        accountRepository.insertAccount(baseModel, owner);
        String token = securityUserService.generateToken();
        mockMvc.perform(get("/account/find-by-name/soyeon lover").header("authorization", token)).andExpect(status().isOk()).andReturn();

        List<AccountModel> resultSet = jdbcTemplate.query("SELECT FROM \"Account\" WHERE LOWER (name) ='soyeon lover'", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(1, resultSet.size());
    }

    @Test
    public void characterNotAllowedExceptionEnRetrieveAccountByName() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(get("/account/find-by-name/soyeon<<<lover").header("authorization", token)).andExpect(status().isBadRequest()).andExpect(content().string("soyeon<<<lover has characters not allowed"));
    }

    @Test
    public void accountNotFoundExceptionEnRetrieveAccountByName() throws Exception {

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
        mockMvc.perform(get("/account/find-by-name/stulinpinguin").header("authorization", token)).andExpect(status().isNotFound()).andExpect(content().string("LA CUENTA stulinpinguin NO FUE ENCONTRADA, POR FAVOR RECTIFICAR"));
    }
}
