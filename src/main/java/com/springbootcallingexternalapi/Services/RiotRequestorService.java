package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.*;
import com.springbootcallingexternalapi.Exceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.QueueNotFoundException;
import com.springbootcallingexternalapi.Exceptions.SummonerIdNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.MasteryInfoModel;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.Repositories.AccountRepository;
import com.springbootcallingexternalapi.Repositories.LeagueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RiotRequestorService {

    private static final String RIOT_TOKEN = "RGAPI-3bc67248-5617-4452-acd6-e0af764e05f1";

    Logger logger = LoggerFactory.getLogger(RiotRequestorService.class);

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ChampionService championService;

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

    public LeagueInfoModel getLeague(String account) throws AccountNotFoundException, AccountDataException, SummonerIdNotFoundException, QueueNotFoundException {
        try {
            String id = getAccountFromRiot(account).getBody().getId();
            String uri = "/lol/league/v4/entries/by-summoner/" + id;
            String queueToFind = "RANKED_SOLO_5x5";
            ResponseEntity<LeagueInfoModel[]> response = requestToRiot(uri, HttpMethod.GET, LeagueInfoModel[].class);
            Optional<LeagueInfoModel> model = Arrays.stream(response.getBody())
                    .filter(leagueInfoModel -> leagueInfoModel.getQueueType().equals(queueToFind) )
                            .findFirst();

            if(model.isPresent()) {
                leagueRepository.insertLeagueInfo(model.get());
                return model.get();
            } else {
                throw new QueueNotFoundException(queueToFind);
            }
        } catch (RestClientException e1) {
            throw new AccountNotFoundException(account);
        }
    }

    public MasteryInfoModel getMastery(String account, String championName) throws AccountNotFoundException, ChampionNotFoundException, ChampionMasteryNotFoundException {
        try {
            String id = getAccountFromRiot(account).getBody().getId();
            Long championId = championService.retrieveChampionIdByChampionName(championName);
            String uri = "/lol/champion-mastery/v4/champion-masteries/by-summoner/" + id + "/by-champion/" + championId;
            return requestToRiot(uri, HttpMethod.GET, MasteryInfoModel.class).getBody();
        }catch (EmptyResultDataAccessException e) {
            throw new ChampionNotFoundException(championName);
        }catch (HttpClientErrorException e1){
            throw  new ChampionMasteryNotFoundException(championName);
        }
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
