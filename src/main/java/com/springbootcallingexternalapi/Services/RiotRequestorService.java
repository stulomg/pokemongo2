package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class RiotRequestorService {

    Logger logger = LoggerFactory.getLogger(RiotRequestorService.class);

    @Autowired
    AccountRepository accountRepository;

    public AccountBaseModel getAccountAndAssignToOwner(String account, String owner) throws AccountDataException, AccountNotFoundException {
        ResponseEntity<AccountBaseModel> acc = getAccountFromRiot(account);
        AccountBaseModel acc2 = Objects.requireNonNull(acc.getBody());
        accountRepository.insertAccount(acc2,owner);

        return acc2;
    }

    public  ResponseEntity<AccountBaseModel> getAccountFromRiot(String account) throws AccountNotFoundException {
        String uri = "https://la1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + account;


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Riot-Token", "RGAPI-cfa76bd9-5230-447b-938f-baaa1eedce07");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try {
            ResponseEntity<AccountBaseModel> response = restTemplate.exchange(uri, HttpMethod.GET, entity, AccountBaseModel.class);
            return response;
        } catch (RestClientException e) {
            throw new AccountNotFoundException(account);
        }
    }

    public String getLeague(String account) throws AccountNotFoundException, AccountDataException {
        try {
            String id = getAccountFromRiot(account).getBody().getId();
            String uri = "https://la1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Riot-Token", "RGAPI-cfa76bd9-5230-447b-938f-baaa1eedce07");
            HttpEntity<String> entity = new HttpEntity<>("", headers);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET ,entity, String.class);
            return response.getBody();
        } catch (AccountNotFoundException e) {
            throw e;
        }
        catch (RestClientException e1){
            throw new AccountNotFoundException(account);
        }
    }
}
