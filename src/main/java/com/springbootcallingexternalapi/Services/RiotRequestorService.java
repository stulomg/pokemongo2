package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.SummonerIdNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.MasteryInfoModel;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.Repositories.AccountRepository;
import com.springbootcallingexternalapi.Repositories.LeagueRepository;
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

    private static final String RIOT_TOKEN = "RGAPI-4408688d-f6ce-404d-a8ed-304f4c459d03";

    Logger logger = LoggerFactory.getLogger(RiotRequestorService.class);

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    LeagueRepository leagueRepository;

    public AccountBaseModel getAccountAndAssignToOwner(String account, String owner) throws AccountDataException, AccountNotFoundException {
        ResponseEntity<AccountBaseModel> acc = getAccountFromRiot(account);
        AccountBaseModel acc2 = Objects.requireNonNull(acc.getBody());
        accountRepository.insertAccount(acc2,owner);
        return acc2;
    }

    public ResponseEntity<AccountBaseModel> getAccountFromRiot(String account) throws AccountNotFoundException {
        String uri = "/lol/summoner/v4/summoners/by-name/" + account;
        try {
            return requestToRiot(uri, HttpMethod.GET, AccountBaseModel.class);
        } catch (RestClientException e) {
            throw new AccountNotFoundException(account);
        }
    }

    public LeagueInfoModel[] getLeague(String account) throws AccountNotFoundException, AccountDataException, SummonerIdNotFoundException {
        try {
            String id = getAccountFromRiot(account).getBody().getId();
            String uri = "/lol/league/v4/entries/by-summoner/" + id;
            ResponseEntity<LeagueInfoModel[]> response = requestToRiot(uri, HttpMethod.GET, LeagueInfoModel[].class);
            logger.info(String.valueOf(response));
            leagueRepository.insertLeagueInfo(response.getBody()[0]);

            return response.getBody();
        } catch (AccountNotFoundException e) {
            throw e;
        } catch (RestClientException e1) {
            throw new AccountNotFoundException(account);
        }
    }

    public MasteryInfoModel getMastery(String account, long idChampion) throws AccountNotFoundException {
        String id = getAccountFromRiot(account).getBody().getId();
        String uri = "/lol/champion-mastery/v4/champion-masteries/by-summoner/" + id + "/by-champion/" + idChampion;
        return requestToRiot(uri, HttpMethod.GET, MasteryInfoModel.class).getBody();
    }

    private <T> ResponseEntity<T> requestToRiot(String uri, HttpMethod method, Class<T> clazz) {
        String finalUrl = "https://la1.api.riotgames.com" + uri;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Riot-Token", RIOT_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        return restTemplate.exchange(finalUrl, method, entity, clazz);
    }
}

