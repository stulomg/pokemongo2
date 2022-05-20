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

    public List<RecommendedRoleModel> recommendedRoleService(RecommendedRoleDataModel data) throws NoDataException, CharacterNotAllowedException, AccountNotFoundException {
        ArrayList accounts = new ArrayList();
        accounts.add(accountRepository.retrieveAccountIdByAccountName(data.getParticipant1()));
        accounts.add(accountRepository.retrieveAccountIdByAccountName(data.getParticipant2()));
        accounts.add(accountRepository.retrieveAccountIdByAccountName(data.getParticipant3()));
        accounts.add(accountRepository.retrieveAccountIdByAccountName(data.getParticipant4()));
        accounts.add(accountRepository.retrieveAccountIdByAccountName(data.getParticipant5()));
        List<RecommendedRoleModel> recommendedRoleModelFinal = new ArrayList<>();
        List<RecommendedRoleModel> listSumRole = recommendedRoleRepository.recommendedRole(accounts);

        ArrayList contador = new ArrayList(accounts.size());
        for (int i = 0; i < accounts.size(); i++) {
            contador.add(-1);
        }
        for (int i = 0; i < accounts.size(); i++) {
            Integer maxRole = 0;
            for (int j = 0; j < listSumRole.size(); j++) {
                if (listSumRole.get(j).getAccount().equals(accounts.get(i))) {
                    if (listSumRole.get(j).getGamesPlayed() > maxRole) {
                        maxRole = listSumRole.get(j).getGamesPlayed();
                        contador.set(i,j);
                    } else if (listSumRole.get(j).getGamesPlayed() == maxRole) {
                        contador.set(i,j);
                    }
                }
            }
        }
        for (int i = 0; i < contador.size(); i++) {
            if (contador.get(i).equals(-1)) {
                RecommendedRoleModel noData = new RecommendedRoleModel(
                        0,
                        0,
                        0
                );
                noData.setAccount((Integer) accounts.get(i));
                recommendedRoleModelFinal.add(noData);
            }else  {
                recommendedRoleModelFinal.add(listSumRole.get((Integer) contador.get(i)));
            }
        }
        return recommendedRoleModelFinal;
    }
}