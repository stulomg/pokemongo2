package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MasteryHistoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

@Repository
public class MasteryRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertMasteryInfo(MasteryHistoryInfoModel masteryHistoryInfoModel) throws AccountDataException {
        String sql = "INSERT INTO \"MasteryHistory\"(champion, \"championPoints\", \"championLevel\", date, account)VALUES (?, ?, ?, ?, ?);";
        Object[] params = {
                masteryHistoryInfoModel.getChampion(),
                masteryHistoryInfoModel.getChampionPoints(),
                masteryHistoryInfoModel.getChampionLevel(),
                masteryHistoryInfoModel.getDate(),
                masteryHistoryInfoModel.getAccount()};
        try {
            jdbcTemplate.update(sql, params);
        } catch (DataAccessException e) {
            throw new AccountDataException(masteryHistoryInfoModel);
        }
    }

    public List<MasteryHistoryModel> AccountMasteryHistory(String account, Integer accountID) throws AccountNotFoundException, CharacterNotAllowedException {
        String sql = "SELECT (SELECT \"ChampionName\" FROM \"Champion\" WHERE \"ChampionId\" = a.champion) as champion, a.\"championPoints\", a.\"championLevel\", a.date, (SELECT \"name\" FROM \"Account\" WHERE \"id_BD\" = a.account) as account\n" +
                "\tFROM \"MasteryHistory\" as a \n" +
                "\tWHERE a.account = ?;";
        Object[] params = {accountID};
        if (isAlpha(account)) {
            List<MasteryHistoryModel> listMastery = jdbcTemplate.query(sql, params,
                    BeanPropertyRowMapper.newInstance(MasteryHistoryModel.class));
            if (listMastery.size() == 0) {
                throw new AccountNotFoundException(account);
            } else return listMastery;
        } else throw new CharacterNotAllowedException(account);
    }
}