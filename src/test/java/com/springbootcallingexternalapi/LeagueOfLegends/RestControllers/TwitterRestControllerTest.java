package com.springbootcallingexternalapi.LeagueOfLegends.RestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Services.SecurityUserService;
import com.springbootcallingexternalapi.Twitter.TwitterRepository.HashtagsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TwitterRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    HashtagsRepository hashtagsRepository;
    @Autowired
    private SecurityUserService securityUserService;

    @Test
    public void insertNewHashtagSuccessfullyTest() throws Exception {
        String token = securityUserService.generateToken();
        mockMvc.perform(post("/insert/twitter/pepito").header("authorization", token)).andExpect(status().isOk()).andReturn();
    }
}