package com.springbootcallingexternalapi.RestControllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountOrOwnerNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Repositories.AccountRepository;
import com.springbootcallingexternalapi.RestControllers.AccountRestController;
import com.springbootcallingexternalapi.Services.AccountService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup(){
        jdbcTemplate.execute("TRUNCATE TABLE \"Accounts\"");
    }

    @Test
    void deleteExitosamenteCasoDefault() throws Exception {



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
        accountRepository.insertAccount(baseModel,owner);

        MvcResult mvcResult = mockMvc.perform(delete("/account/delete/kusi/Soyeon Lover")).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals("Delete successfully",content);

        List<AccountModel> resultSet = jdbcTemplate.query("SELECT * FROM \"Accounts\"", BeanPropertyRowMapper.newInstance(AccountModel.class));
        Assertions.assertEquals(0,resultSet.size());
    }

    @Test
    void
}
