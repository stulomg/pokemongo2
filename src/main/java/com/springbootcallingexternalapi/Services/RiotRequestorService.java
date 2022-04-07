package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.ChampionsExceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.Exceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Exceptions.OwnerExceptions.OwnerNotAllowed;
import com.springbootcallingexternalapi.Exceptions.QueueNotFoundException;
import com.springbootcallingexternalapi.Exceptions.SummonerNotFoundException;
import com.springbootcallingexternalapi.Models.AccountBaseModel;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.Repositories.AccountRepository;
import com.springbootcallingexternalapi.Repositories.LeagueRepository;
import com.springbootcallingexternalapi.Repositories.MasteryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
public class RiotRequestorService {

    private static final String RIOT_TOKEN = "RGAPI-70cec9c6-6fdd-4f2a-aa14-e3fbb6759836";

    Logger logger = LoggerFactory.getLogger(RiotRequestorService.class);

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ChampionService championService;
    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    MasteryRepository masteryRepository;

    public AccountBaseModel getAccountAndAssignToOwner(String account, String owner) throws AccountDataException, AccountNotFoundException, OwnerNotAllowed, CharacterNotAllowedException {
        ResponseEntity<AccountBaseModel> acc = getAccountFromRiot(account.toLowerCase(Locale.ROOT));
        AccountBaseModel acc2 = Objects.requireNonNull(acc.getBody());

            accountRepository.insertAccount(acc2, owner.toLowerCase(Locale.ROOT));
            return acc2;

    }

    public ResponseEntity<AccountBaseModel> getAccountFromRiot(String account) throws AccountNotFoundException {
        String uri = "/lol/summoner/v4/summoners/by-name/" + account;
        try {
            return requestToRiot(uri, HttpMethod.GET, AccountBaseModel.class);
        } catch (RestClientException e) {
            logger.info(e.getMessage());
            throw new AccountNotFoundException(account);
        }
    }

    public LeagueInfoModel getLeague(String account) throws AccountNotFoundException, AccountDataException, QueueNotFoundException, CharacterNotAllowedException {
        try {
            String id = getAccountFromRiot(account).getBody().getId();
            String uri = "/lol/league/v4/entries/by-summoner/" + id;
            String queueToFind = "RANKED_SOLO_5x5";
            ResponseEntity<LeagueInfoModel[]> response = requestToRiot(uri, HttpMethod.GET, LeagueInfoModel[].class);
            Optional<LeagueInfoModel> model = Arrays.stream(response.getBody())
                    .filter(leagueInfoModel -> leagueInfoModel.getQueueType().equals(queueToFind))
                    .findFirst();
            if (model.isPresent()) {
                leagueRepository.insertLeagueInfo(model.get());
                return model.get();
            } else {
                throw new QueueNotFoundException(queueToFind);
            }
        } catch (RestClientException e1) {
            throw new AccountNotFoundException(account);
        }
    }

    public MasteryHistoryInfoModel getMastery(String account, String championName) throws AccountNotFoundException, ChampionNotFoundException, ChampionMasteryNotFoundException, CharacterNotAllowedException, AccountDataException {
        try {
            String id = getAccountFromRiot(account).getBody().getId();
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            Long championId = championService.retrieveChampionIdByChampionName(championName.toLowerCase(Locale.ROOT));
            String uri = "/lol/champion-mastery/v4/champion-masteries/by-summoner/" + id + "/by-champion/" + championId;
            ResponseEntity<MasteryHistoryInfoModel> response = requestToRiot(uri, HttpMethod.GET, MasteryHistoryInfoModel.class);
            MasteryHistoryInfoModel model = response.getBody();
            model.setTimestamp(timeStamp);
            model.setChampionName(championName);
            model.setAccount(account);
            masteryRepository.insertMasteryInfo(model);
            return model;
        }catch (EmptyResultDataAccessException e) {
            throw new ChampionNotFoundException(championName);
        }catch (HttpClientErrorException e1){
            throw  new ChampionMasteryNotFoundException(championName);
        } catch (CharacterNotAllowedException e) {
            throw new CharacterNotAllowedException(championName);
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
