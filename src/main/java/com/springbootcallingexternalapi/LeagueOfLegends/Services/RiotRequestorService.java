package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountExistsException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.ChampionsExceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.QueueNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.*;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.*;
import com.springbootcallingexternalapi.LeagueOfLegends.Util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

@Service
public class RiotRequestorService {

    private static final String RIOT_TOKEN = "RGAPI-d0cb32df-0eb8-42b4-a6b8-d9934ba67fb3";

    Logger logger = LoggerFactory.getLogger(RiotRequestorService.class);

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ChampionService championService;
    @Autowired
    LeagueService leagueService;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    MasteryRepository masteryRepository;
    @Autowired
    MatchRepository matchRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ServerRepository serverRepository;

    public AccountBaseModel getAccountAndAssignToOwner(String account, String owner) throws AccountDataException, AccountNotFoundException, CharacterNotAllowedException, OwnerNotFoundException {
        ResponseEntity<AccountBaseModel> acc = getAccountFromRiot(account.toLowerCase(Locale.ROOT));
        AccountBaseModel acc2 = Objects.requireNonNull(acc.getBody());
        Long ownerID = ownerRepository.retrieveOwnerIdByOwnerName(owner.toLowerCase(Locale.ROOT));
        try {
            accountRepository.insertAccount(acc2, Math.toIntExact(ownerID));
        }catch (AccountExistsException e){
            accountRepository.accountUpdateExisting(acc2, Math.toIntExact(ownerID));
        }
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

    public LeagueInfoModel getSoloqLeague(String account, String owner) throws AccountNotFoundException, AccountDataException, QueueNotFoundException, CharacterNotAllowedException {
        try {
            String id = getAccountFromRiot(account).getBody().getId();
            String uri = "/lol/league/v4/entries/by-summoner/" + id;
            String queueToFind = "RANKED_SOLO_5x5";
            ResponseEntity<LeagueInfoModel[]> response = requestToRiot(uri, HttpMethod.GET, LeagueInfoModel[].class);
            Optional<LeagueInfoModel> model = Arrays.stream(response.getBody())
                    .filter(leagueInfoModel -> leagueInfoModel.getQueueType().equals(queueToFind))
                    .findFirst();

            if (model.isPresent()) {
                LeagueInfoModel lim = model.get();
                lim.setOwner(owner);
                leagueService.insertLeagueInfo(lim);
                return lim;
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
        } catch (EmptyResultDataAccessException e) {
            throw new ChampionNotFoundException(championName);
        } catch (HttpClientErrorException e1) {
            throw new ChampionMasteryNotFoundException(championName);
        } catch (CharacterNotAllowedException e) {
            throw new CharacterNotAllowedException(championName);
        }
    }

    public <T> ResponseEntity<T> requestToRiot(String uri, HttpMethod method, Class<T> clazz) {
        String finalUrl = "https://la1.api.riotgames.com" + uri;

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Riot-Token", RIOT_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        return restTemplate.exchange(finalUrl, method, entity, clazz);
    }

    private <T> ResponseEntity<T> requestToRiot2(String uri, HttpMethod method, Class<T> clazz) {
        String finalUrl = "https://americas.api.riotgames.com" + uri;

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Riot-Token", RIOT_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        return restTemplate.exchange(finalUrl, method, entity, clazz);
    }

    public CurrentGameInfoBaseModel getLiveMatch(String account) throws AccountNotFoundException, CharacterNotAllowedException {

        if (isAlpha(account)) {

            ResponseEntity <AccountBaseModel> response2 = getAccountFromRiot(account);
            String id = response2.getBody().getId();
            String uri = "/lol/spectator/v4/active-games/by-summoner/" + id;
            ResponseEntity<CurrentGameInfoBaseModel> response = requestToRiot(uri, HttpMethod.GET, CurrentGameInfoBaseModel.class);

            return response.getBody();
        } else throw new CharacterNotAllowedException(account);
    }

    @Scheduled(cron = " 0 0 */2 * * ?")
    public Object serverStatus() {

        String uri = "/lol/status/v4/platform-data";
        ResponseEntity<MaintenancesStatusModel> response = requestToRiot(uri, HttpMethod.GET, MaintenancesStatusModel.class);
        MaintenancesStatusModel model = response.getBody();
        model.getName();
        model.getLocales();
        model.getMaintenances();
        model.getIncidents();
        serverRepository.insertServerStatus(model);

        return response.getBody();
    }

    public List<Object> getListMatches(String account) throws AccountNotFoundException, ChampionNotFoundException, CharacterNotAllowedException, AccountDataException, ChampionMasteryNotFoundException {

        String puuid = getAccountFromRiot(account).getBody().getPuuid();
        String uri = "/lol/match/v5/matches/by-puuid/" + puuid + "/ids?queue=420&start=0&count=5";

        ResponseEntity<List> response = requestToRiot2(uri, HttpMethod.GET, List.class);
        List<String> listMatches = response.getBody();

        List<Object> list = new ArrayList<Object>();

        for (int i = 0; i < listMatches.size(); i++) {

            String elemento = listMatches.get(i);

            GameSuperMetaDataModel response2 = getListData(elemento).getBody();

            GameDataModel[] response3 = response2.getInfo().getParticipants();
            Optional<GameDataModel> model = Arrays.stream(response3)
                    .filter(GameDataModel -> GameDataModel.getSummonerName().equals(account))
                    .findFirst();

            GameDataModel lim = model.get();
            int championpoints = getMastery(account,lim.getChampionName()).getChampionPoints();
            lim.setChampionPoints(championpoints);

            matchRepository.insertMatchData(lim);

            list.add(model);
        }

        return list;
    }

    public ResponseEntity<GameSuperMetaDataModel> getListData(String matchId) {

        String uri = "/lol/match/v5/matches/" + matchId;
        ResponseEntity<GameSuperMetaDataModel> response = requestToRiot2(uri, HttpMethod.GET, GameSuperMetaDataModel.class);
        return response;
    }

    public ResponseEntity<TeamAccountsMetaData> getAccountsForClash (String account) throws AccountNotFoundException, ChampionNotFoundException, CharacterNotAllowedException, AccountDataException, ChampionMasteryNotFoundException {
        String id = getAccountFromRiot(account).getBody().getId();
        String uri = "/lol/clash/v1/players/by-summoner/" + id;

        ResponseEntity<AccountForClashData> response = requestToRiot(uri,HttpMethod.GET,AccountForClashData.class);
        String teamId = response.getBody().getTeamId();

        ResponseEntity<TeamAccountsMetaData> response2 = getClashParticipantsByTeamId(teamId);

        List<Object> clashSummoners  = new ArrayList<>();

        return response2;
    }

    private ResponseEntity<TeamAccountsMetaData> getClashParticipantsByTeamId (String teamId){
        String uri = "/lol/clash/v1/teams/" + teamId;

        ResponseEntity<TeamAccountsMetaData> response = requestToRiot(uri,HttpMethod.GET,TeamAccountsMetaData.class);
        return response;
    }

    private String getSummonerNameBySummonerId (String summonerId){
       String uri = "/lol/summoner/v4/summoners/" + summonerId;

        return requestToRiot(uri,HttpMethod.GET,String.class).toString();
    }

    public Object fillTablePlayersRelationship(String account) throws ChampionNotFoundException, CharacterNotAllowedException, AccountDataException, ChampionMasteryNotFoundException, AccountNotFoundException {


        String puuid = getAccountFromRiot(account).getBody().getPuuid();
        String uri = "/lol/match/v5/matches/by-puuid/" + puuid + "/ids?queue=420&start=0&count=20";

        ResponseEntity<List> response = requestToRiot2(uri, HttpMethod.GET, List.class);
        List<String> listMatches = response.getBody();

        List<String> listParticipants = new ArrayList<>();

        for (int i = 0; i < listMatches.size(); i++) {

            String elemento = listMatches.get(i);

            /*GameDataModel[] response3 = response2.getInfo().getParticipants();
            Optional<GameDataModel> model = Arrays.stream(response3)
                    .filter(GameDataModel::getSummonerName);*/

            //GameDataModel lim = model.get();

        //matchrepository


    }
        return uri;
    }
}