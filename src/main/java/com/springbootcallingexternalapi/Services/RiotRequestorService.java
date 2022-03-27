package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RiotRequestorService {

    Logger logger = LoggerFactory.getLogger(RiotRequestorService.class);

    public AccountBaseModel getAccountFromRiot(String account) throws AccountNotFoundException {
        String uri = "https://la1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + account;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Riot-Token", "RGAPI-2bca1181-b630-46c7-9755-61cf3c024c6c");

        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try {
            ResponseEntity<AccountBaseModel> response = restTemplate.exchange(uri, HttpMethod.GET, entity, AccountBaseModel.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new AccountNotFoundException(account);
        }
    }
}
