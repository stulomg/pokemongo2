package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountExistsOrNotException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundDBException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.ClashIsNotAvailable;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Position.PositionNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.QueueNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.*;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String RIOT_TOKEN = "RGAPI-f727bc42-235d-4320-912f-5c7befb2d509";

    Logger logger = LoggerFactory.getLogger(RiotRequestorService.class);

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ChampionService championService;
    @Autowired
    LeagueService leagueService;
    @Autowired
    OwnerService ownerService;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    MasteryRepository masteryRepository;
    @Autowired
    MatchRepository matchRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ServerRepository serverRepository;
    @Autowired
    RelationshipRepository relationshipRepository;
    @Autowired
    CurrentGameRunesRepository currentGameRunesRepository;

    public AccountBaseModel getAccountAndAssignToOwner(String account, String owner) throws AccountDataException, AccountNotFoundException, CharacterNotAllowedException, OwnerNotFoundException {
        ResponseEntity<AccountBaseModel> acc = getAccountFromRiot(account.toLowerCase(Locale.ROOT));
        AccountBaseModel acc2 = Objects.requireNonNull(acc.getBody());
        Long ownerID = ownerService.retrieveOwnerIdByOwnerName(owner.toLowerCase(Locale.ROOT));
        try {
            accountRepository.insertAccount(acc2, Math.toIntExact(ownerID));
        }catch (AccountExistsOrNotException e){
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

    public LeagueInfoModel getSoloqLeague(String account) throws AccountNotFoundDBException, AccountNotFoundException, AccountDataException, QueueNotFoundException, CharacterNotAllowedException {
        try {
            String id = accountRepository.retrieveIdRiotByAccount(account);
            String uri = "/lol/league/v4/entries/by-summoner/" + id;
            String queueToFind = "RANKED_SOLO_5x5";
            ResponseEntity<LeagueInfoModel[]> response = requestToRiot(uri, HttpMethod.GET, LeagueInfoModel[].class);
            Optional<LeagueInfoModel> model = Arrays.stream(response.getBody())
                    .filter(leagueInfoModel -> leagueInfoModel.getQueueType().equals(queueToFind))
                    .findFirst();
            if (model.isPresent()) {
                LeagueInfoModel lim = model.get();
                leagueService.insertLeagueInfo(lim,account);
                return lim;
            } else {
                throw new QueueNotFoundException(queueToFind);
            }
        } catch (RestClientException e1) {
            throw new AccountNotFoundException(account);
        } catch (AccountNotFoundDBException e){
            throw new AccountNotFoundDBException(account);
        }
    }

    public MasteryHistoryInfoModel getMastery(String account, String championName) throws AccountNotFoundException, ChampionNotFoundException, ChampionMasteryNotFoundException, CharacterNotAllowedException, AccountDataException, AccountNotFoundDBException {
        String id = accountRepository.retrieveIdRiotByAccount(account);
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        try {
            Long championId = championService.retrieveChampionIdByChampionName(championName.toLowerCase(Locale.ROOT));
            String uri = "/lol/champion-mastery/v4/champion-masteries/by-summoner/" + id + "/by-champion/" + championId;
            ResponseEntity<MasteryHistoryInfoModel> response = requestToRiot(uri, HttpMethod.GET, MasteryHistoryInfoModel.class);
            MasteryHistoryInfoModel model = response.getBody();
            model.setDate(timeStamp);
            Integer accountID = Math.toIntExact(accountRepository.retrieveAccountIdByAccountName(account));
            model.setAccount(accountID);
            model.setChampion(Math.toIntExact(championId));
            masteryRepository.insertMasteryInfo(model);
            return model;
        } catch (EmptyResultDataAccessException e) {
            throw new ChampionNotFoundException(championName);
        } catch (HttpClientErrorException e1) {
            throw new ChampionMasteryNotFoundException(championName);
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

    public CurrentGameInfoBaseModel getLiveMatch(String account) throws AccountNotFoundException, CharacterNotAllowedException, AccountNotFoundDBException {
        if (isAlpha(account)) {
            String id = accountRepository.retrieveIdRiotByAccount(account);
            String uri = "/lol/spectator/v4/active-games/by-summoner/" + id;
            ResponseEntity<CurrentGameInfoBaseModel> response = requestToRiot(uri, HttpMethod.GET, CurrentGameInfoBaseModel.class);
            return response.getBody();

        } else throw new CharacterNotAllowedException(account);
    }

    public CurrentGameInfoRuneModel getCurrentGameRunes(String account) throws AccountNotFoundException, CharacterNotAllowedException, AccountNotFoundDBException {

        if (isAlpha(account)) {
            String id = accountRepository.retrieveIdRiotByAccount(account);
            String uri = "/lol/spectator/v4/active-games/by-summoner/" + id;
            ResponseEntity<CurrentGameInfoRuneModel> response2 = requestToRiot(uri, HttpMethod.GET, CurrentGameInfoRuneModel.class);
            CurrentGameInfoRuneModel model = response2.getBody();

            currentGameRunesRepository.insertRunes(model);

            return response2.getBody();

        } else throw new CharacterNotAllowedException(account);
    }

    @Scheduled(cron = " 0 0 */2 * * ?")
    public Object serverStatus() {
        String uri = "/lol/status/v4/platform-data";
        ResponseEntity<MaintenancesStatusModel> response = requestToRiot(uri, HttpMethod.GET, MaintenancesStatusModel.class);
        MaintenancesStatusModel model = response.getBody();

        serverRepository.insertServerStatus(model);
        return response.getBody();
    }

    public List<Object> getListMatches(String account) throws AccountNotFoundException, ChampionNotFoundException, CharacterNotAllowedException, AccountDataException, ChampionMasteryNotFoundException, PositionNotFoundException, AccountNotFoundDBException {

        String puuid = accountRepository.retrievePuuidRiotByAccount(account);
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
            Integer accountID = Math.toIntExact(accountRepository.retrieveAccountIdByAccountName(lim.getSummonerName()));
            Integer positionID = Math.toIntExact(positionRepository.retrievePositionIdByPositionName(lim.getIndividualPosition()));
            Integer championID = Math.toIntExact(championService.retrieveChampionIdByChampionName(lim.getChampionName()));
            matchRepository.insertIndividualMatchData(lim,accountID,positionID,championID);
            list.add(model);
        }
        return list;
    }

    public ResponseEntity<GameSuperMetaDataModel> getListData(String matchId) {
        String uri = "/lol/match/v5/matches/" + matchId;
        ResponseEntity<GameSuperMetaDataModel> response = requestToRiot2(uri, HttpMethod.GET, GameSuperMetaDataModel.class);
        return response;
    }

    public ResponseEntity<TeamAccountsMetaDataModel> getAccountsForClash(String account) throws AccountNotFoundException, ChampionNotFoundException, CharacterNotAllowedException, AccountDataException, ChampionMasteryNotFoundException, AccountNotFoundDBException {
        String id = accountRepository.retrieveIdRiotByAccount(account);
        String uri = "/lol/clash/v1/players/by-summoner/" + id;
        ResponseEntity<AccountForClashDataModel> response = requestToRiot(uri,HttpMethod.GET, AccountForClashDataModel.class);
        String teamId = response.getBody().getTeamId();
        ResponseEntity<TeamAccountsMetaDataModel> response2 = getClashParticipantsByTeamId(teamId);
        return response2;
    }

    private ResponseEntity<TeamAccountsMetaDataModel> getClashParticipantsByTeamId (String teamId){
        String uri = "/lol/clash/v1/teams/" + teamId;
        ResponseEntity<TeamAccountsMetaDataModel> response = requestToRiot(uri,HttpMethod.GET, TeamAccountsMetaDataModel.class);
        return response;
    }

    private String getSummonerNameBySummonerId(String summonerId) {
        String uri = "/lol/summoner/v4/summoners/" + summonerId;
        return requestToRiot(uri, HttpMethod.GET, String.class).toString();
    }

    public Object playersRelationship (String account1, String account2) throws CharacterNotAllowedException, AccountNotFoundException {
        List<String> list1 = relationshipRepository.getPlayersMatched(account1);
        List<String> list2 = relationshipRepository.getPlayersMatched(account2);
        list2.retainAll(list1);

        if (list2.isEmpty() || list2 == null) {
            return new ResponseEntity<>("The players have no relation", HttpStatus.OK);
        } else {
            return list2.toString();
        }
    }
}