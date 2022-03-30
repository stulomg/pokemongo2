package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.MasteryInfoModel;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.Repositories.AccountRepository;
import com.springbootcallingexternalapi.Repositories.ChampionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class RiotRequestorService {

    private static final String RIOT_TOKEN = "RGAPI-230e13c3-a99e-4d26-a4ea-a5fad90fab8e";

    Logger logger = LoggerFactory.getLogger(RiotRequestorService.class);

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ChampionService championService;


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
        headers.add("X-Riot-Token", RIOT_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try {
            ResponseEntity<AccountBaseModel> response = restTemplate.exchange(uri, HttpMethod.GET, entity, AccountBaseModel.class);
            return response;
        } catch (RestClientException e) {
            throw new AccountNotFoundException(account);
        }
    }
    public LeagueInfoModel[] getLeague(String account) throws AccountNotFoundException, AccountDataException {
        try {
            String id = getAccountFromRiot(account).getBody().getId();
            logger.info(id);
            String uri = "https://la1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Riot-Token", RIOT_TOKEN);
            HttpEntity<String> entity = new HttpEntity<>("", headers);
            ResponseEntity<LeagueInfoModel[]> response = restTemplate.exchange(uri, HttpMethod.GET ,entity, LeagueInfoModel[].class);
            return response.getBody();
        } catch (AccountNotFoundException e) {
            throw e;
        }
        catch (RestClientException e1){
            throw new AccountNotFoundException(account);
        }
    }

    public MasteryInfoModel getMastery (String account, long idChampion) throws AccountNotFoundException {
        String id = getAccountFromRiot(account).getBody().getId();
        String championName = championService.retrieveChampionNameByChampionId(idChampion);
        String uri = "https://la1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/" + id + "/by-champion/" + idChampion;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Riot-Token", RIOT_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("",headers);
        ResponseEntity<MasteryInfoModel> response = restTemplate.exchange(uri, HttpMethod.GET , entity, MasteryInfoModel.class);
        return response.getBody();
    }
}