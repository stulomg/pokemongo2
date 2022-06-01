package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashChampionModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashDataModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashLogicModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashResponseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashRoleModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedClashWinRateModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.RecommendedClashRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class host the functions that give you recomendations about clash opponents.
 */
@Service
public class RecommendedClashService {

  @Autowired
  RecommendedClashRepository recommendedClashRepository;
  @Autowired
  AccountRepository accountRepository;

  /**
   * This function shows you the opponent recommended champion.
   */
  public List<RecommendedClashResponseModel> recommendedClashService(
      List<RecommendedClashDataModel> data)
      throws NoDataException, CharacterNotAllowedException, AccountNotFoundException {
    ArrayList accounts = new ArrayList();
    for (int i = 0; i < data.size(); i++) {
      accounts.add(accountRepository.retrieveAccountIdByAccountName(data.get(i).getParticipant()));
    }
    List<RecommendedClashResponseModel> recommendedClashModelFinal = new ArrayList<>();
    List<RecommendedClashRoleModel> recommendedClashRoleModel = recommendedRoleService(accounts);
    List<RecommendedClashLogicModel> recommendedClashChampionModel = recommendedChampionService(
        accounts);

    ArrayList championReccomended = new ArrayList(accounts.size());
    for (int i = 0; i < accounts.size(); i++) {
      championReccomended.add(0);
    }

    for (int i = 0; i < accounts.size(); i++) {
      Double champValue = 0.0;
      for (int j = 0; j < recommendedClashChampionModel.size(); j++) {
        if (accounts.get(i) == recommendedClashChampionModel.get(j).getAccount()) {
          Double gamesChamp = Double.valueOf(
              recommendedClashChampionModel.get(j).getGamesChampion());
          Double totalGamesChampion = Double.valueOf(
              recommendedClashChampionModel.get(j).getTotalGamesAccount());
          Double masteryChampion = Double.valueOf(
              recommendedClashChampionModel.get(j).getMasteryChampion());
          Double totalMasteryChampion = Double.valueOf(
              recommendedClashChampionModel.get(j).getTotalMasteryAccount());
          Double gamesWinChampion = Double.valueOf(
              recommendedClashChampionModel.get(j).getGamesWinChampion());

          Double value =
              (gamesChamp / totalGamesChampion) + (masteryChampion / totalMasteryChampion) + (
                  gamesWinChampion / gamesChamp);
          if (value > champValue) {
            champValue = value;
            championReccomended.set(i, recommendedClashChampionModel.get(j).getChampion());
          }
        }
      }
    }
    for (int i = 0; i < recommendedClashRoleModel.size(); i++) {
      RecommendedClashResponseModel dataResponse = new RecommendedClashResponseModel(
          0,
          0,
          0,
          0
      );
      dataResponse.setAccount(recommendedClashRoleModel.get(i).getAccount());
      dataResponse.setRecommendPosition(recommendedClashRoleModel.get(i).getRecommendPosition());
      dataResponse.setGamesPlayed(recommendedClashRoleModel.get(i).getGamesPlayed());
      dataResponse.setRecommendChampion((Integer) championReccomended.get(i));
      recommendedClashModelFinal.add(dataResponse);
    }
    return recommendedClashModelFinal;
  }

  /**
   * This function recomend a role given an account.
   */
  public List<RecommendedClashRoleModel> recommendedRoleService(ArrayList accounts)
      throws NoDataException {
    List<RecommendedClashRoleModel> recommendedClashRoleModelFinal = new ArrayList<>();
    List<RecommendedClashRoleModel> listSumRole = recommendedClashRepository.recommendedRole(
        accounts);
    ArrayList counter = new ArrayList(accounts.size());
    for (int i = 0; i < accounts.size(); i++) {
      counter.add(-1);
    }
    for (int i = 0; i < accounts.size(); i++) {
      Integer maxRole = 0;
      for (int j = 0; j < listSumRole.size(); j++) {
        if (listSumRole.get(j).getAccount().equals(accounts.get(i))) {
          if (listSumRole.get(j).getGamesPlayed() >= maxRole) {
            maxRole = listSumRole.get(j).getGamesPlayed();
            counter.set(i, j);
          }
        }
      }
    }
    for (int i = 0; i < counter.size(); i++) {
      if (counter.get(i).equals(-1)) {
        RecommendedClashRoleModel noData = new RecommendedClashRoleModel(
            0,
            0,
            0
        );
        noData.setAccount((Integer) accounts.get(i));
        recommendedClashRoleModelFinal.add(noData);
      } else {
        recommendedClashRoleModelFinal.add(listSumRole.get((Integer) counter.get(i)));
      }
    }
    return recommendedClashRoleModelFinal;
  }

  /**
   * This function recomend you a champion giving an account.
   */
  public List<RecommendedClashLogicModel> recommendedChampionService(ArrayList accounts)
      throws NoDataException {
    List<RecommendedClashChampionModel> recommendedChampion = recommendedClashRepository
        .recommendChampion(
            accounts);
    List<RecommendedClashWinRateModel> championWin = recommendedClashRepository.championWin(
        accounts);
    ArrayList totalGamesAccount = new ArrayList(accounts.size());
    for (int i = 0; i < accounts.size(); i++) {
      totalGamesAccount.add(0);
    }
    ArrayList totalMasteryAccount = new ArrayList(accounts.size());
    for (int i = 0; i < accounts.size(); i++) {
      totalMasteryAccount.add(0);
    }
    for (int i = 0; i < accounts.size(); i++) {
      Integer totalGames = 0;
      Integer totalMastery = 0;
      for (int j = 0; j < recommendedChampion.size(); j++) {
        if (recommendedChampion.get(j).getAccount().equals(accounts.get(i))) {
          totalGames =
              recommendedChampion.get(j).getGamesplayed() + (Integer) totalGamesAccount.get(i);
          totalGamesAccount.set(i, totalGames);
          totalMastery =
              recommendedChampion.get(j).getMaxmastery() + (Integer) totalMasteryAccount.get(i);
          totalMasteryAccount.set(i, totalMastery);
        }
      }
    }

    List<RecommendedClashLogicModel> recommendedClashLogicModel = new ArrayList<>();
    for (int i = 0; i < accounts.size(); i++) {
      for (int j = 0; j < recommendedChampion.size(); j++) {
        if (accounts.get(i) == recommendedChampion.get(j).getAccount()) {
          RecommendedClashLogicModel data = new RecommendedClashLogicModel(
              0,
              0,
              0,
              0,
              0,
              0,
              0
          );
          data.setAccount(recommendedChampion.get(j).getAccount());
          data.setChampion(recommendedChampion.get(j).getChampion());
          data.setGamesChampion(recommendedChampion.get(j).getGamesplayed());
          data.setTotalGamesAccount((Integer) totalGamesAccount.get(i));
          data.setMasteryChampion(recommendedChampion.get(j).getMaxmastery());
          data.setTotalMasteryAccount((Integer) totalMasteryAccount.get(i));
          for (int k = 0; k < championWin.size(); k++) {
            if (recommendedChampion.get(j).getAccount() == championWin.get(k).getAccount()) {
              if (recommendedChampion.get(j).getChampion() == championWin.get(k).getChampion()) {
                data.setGamesWinChampion(championWin.get(k).getWin());
                break;
              } else {
                data.setGamesWinChampion(0);
              }
            }
          }
          recommendedClashLogicModel.add(data);
        }
      }
    }
    return recommendedClashLogicModel;
  }
}