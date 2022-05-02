package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedExceptionOwner;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class LeagueService {

    @Autowired
    LeagueRepository leagueRepository;

    public void insertLeagueInfo(LeagueInfoModel leagueInfoModel) throws CharacterNotAllowedException, AccountDataException {
        leagueInfoModel.setElo(calculateElo(leagueInfoModel.getTier(), leagueInfoModel.getRank(), leagueInfoModel.getLeaguePoints()));
        leagueInfoModel.setDate(new Timestamp(System.currentTimeMillis()));
        leagueRepository.insertLeagueInfo(leagueInfoModel, leagueInfoModel.getOwner());
    }

    public Object divisionHistory(String account) throws CharacterNotAllowedException, AccountNotFoundException {
        return leagueRepository.divisionHistory(account);
    }

    public Object getMaxDivision(String owner, String owner2) throws OwnerNotFoundException, CharacterNotAllowedExceptionOwner {

        return leagueRepository.getMaxDivision(owner, owner2);

    }

    private int calculateElo(String tier, String rank, int points) {
        int tierValue = getTierValue(tier);
        int rankValue = getRankValue(rank);
        return tierValue + rankValue + points;

    }


    private int getTierValue(String tier) {

        int v = 0;

        if (tier == null || tier == "") {
            return v;
        }

        switch (tier) {

            case "IRON":
                v = 1000;
                break;
            case "BRONZE":
                v = 2000;
                break;
            case "SILVER":
                v = 3000;
                break;
            case "GOLD":
                v = 4000;
                break;
            case "PLATINUM":
                v = 5000;
                break;
            case "DIAMOND":
                v = 6000;
                break;
            case "MASTER":
                v = 7000;
                break;
            case "GRANDMASTER":
                v = 8000;
                break;
            case "CHALLENGER":
                v = 9000;
                break;

            default:
                break;
        }
        return v;
    }

    private int getRankValue(String rank) {

        int v = 0;

        if (rank == null || rank == "") {
            return v;
        }

        switch (rank) {

            case "I":
                v = 400;
                break;
            case "II":
                v = 300;
                break;
            case "III":
                v = 200;
                break;
            case "IV":
                v = 100;
                break;
            default:
                break;
        }
        return v;
    }
}

