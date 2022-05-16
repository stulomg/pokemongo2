package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedRoleDataModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedRoleModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.AccountRepository;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.RecommendedRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendedRoleService {
    @Autowired
    RecommendedRoleRepository recommendedRoleRepository;
    @Autowired
    AccountRepository accountRepository;

    public List<RecommendedRoleModel> recommendedRoleRepository(RecommendedRoleDataModel data) throws NoDataException, CharacterNotAllowedException, AccountNotFoundException {
        Integer part1 = Math.toIntExact(accountRepository.retrieveAccountIdByAccountName(data.getParticipant1()));
        Integer part2 = Math.toIntExact(accountRepository.retrieveAccountIdByAccountName(data.getParticipant2()));
        Integer part3 = Math.toIntExact(accountRepository.retrieveAccountIdByAccountName(data.getParticipant3()));
        Integer part4 = Math.toIntExact(accountRepository.retrieveAccountIdByAccountName(data.getParticipant4()));
        Integer part5 = Math.toIntExact(accountRepository.retrieveAccountIdByAccountName(data.getParticipant5()));
        List<RecommendedRoleModel> recommendedRoleModelFinal = new ArrayList<>();
        List<RecommendedRoleModel> listSumRole = recommendedRoleRepository.recommendedRole(part1,part2,part3,part4,part5);
        int maxRole1 = 0;
        int maxRole2 = 0;
        int maxRole3 = 0;
        int maxRole4 = 0;
        int maxRole5 = 0;
        int contador1 = -1;
        int contador2 = -1;
        int contador3 = -1;
        int contador4 = -1;
        int contador5 = -1;
        for (int i = 0; i < listSumRole.size(); i++) {
            if (listSumRole.get(i).getAccount().equals(part1)) {
                if (listSumRole.get(i).getGamesPlayed() > maxRole1) {
                    maxRole1 = listSumRole.get(i).getGamesPlayed();
                    contador1 = i;
                } else if (listSumRole.get(i).getGamesPlayed() == maxRole1) {
                    contador1 = i;
                }
            }
            if (listSumRole.get(i).getAccount().equals(part2)) {
                if (listSumRole.get(i).getGamesPlayed() > maxRole2) {
                    maxRole2 = listSumRole.get(i).getGamesPlayed();
                    contador2 = i;
                } else if (listSumRole.get(i).getGamesPlayed() == maxRole2) {
                    contador2 = i;
                }
            }
            if (listSumRole.get(i).getAccount().equals(part3)) {
                if (listSumRole.get(i).getGamesPlayed() > maxRole3) {
                    maxRole3 = listSumRole.get(i).getGamesPlayed();
                    contador3 = i;
                } else if (listSumRole.get(i).getGamesPlayed() == maxRole3) {
                    contador3 = i;
                }
            }
            if (listSumRole.get(i).getAccount().equals(part4)) {
                if (listSumRole.get(i).getGamesPlayed() > maxRole4) {
                    maxRole4 = listSumRole.get(i).getGamesPlayed();
                    contador4 = i;
                } else if (listSumRole.get(i).getGamesPlayed() == maxRole4) {
                    contador4 = i;
                }
            }
            if (listSumRole.get(i).getAccount().equals(part5)) {
                if (listSumRole.get(i).getGamesPlayed() > maxRole5) {
                    maxRole5 = listSumRole.get(i).getGamesPlayed();
                    contador5 = i;
                } else if (listSumRole.get(i).getGamesPlayed() == maxRole5) {
                    contador5 = i;
                }
            }
        }

        if (contador1 != -1) {
            recommendedRoleModelFinal.add(listSumRole.get(contador1));
        } else if (contador1 == -1) {
            RecommendedRoleModel noData = new RecommendedRoleModel(
                    0,
                    0,
                    0
            );
            noData.setAccount(part1);
            recommendedRoleModelFinal.add(noData);
        }
        if (contador2 != -1) {
            recommendedRoleModelFinal.add(listSumRole.get(contador2));
        } else if (contador2 == -1) {
            RecommendedRoleModel noData = new RecommendedRoleModel(
                    0,
                    0,
                    0
            );
            noData.setAccount(part2);
            recommendedRoleModelFinal.add(noData);
        }
        if (contador3 != -1) {
            recommendedRoleModelFinal.add(listSumRole.get(contador3));
        } else if (contador3 == -1) {
            RecommendedRoleModel noData = new RecommendedRoleModel(
                    0,
                    0,
                    0
            );
            noData.setAccount(part3);
            recommendedRoleModelFinal.add(noData);
        }
        if (contador4 != -1) {
            recommendedRoleModelFinal.add(listSumRole.get(contador4));
        } else if (contador4 == -1) {
            RecommendedRoleModel noData = new RecommendedRoleModel(
                    0,
                    0,
                    0
            );
            noData.setAccount(part4);
            recommendedRoleModelFinal.add(noData);
        }
        if (contador5 != -1) {
            recommendedRoleModelFinal.add(listSumRole.get(contador5));
        } else if (contador5 == -1) {
            RecommendedRoleModel noData = new RecommendedRoleModel(
                    0,
                    0,
                    0
            );
            noData.setAccount(part5);
            recommendedRoleModelFinal.add(noData);
        }
        return recommendedRoleModelFinal;
    }

}




