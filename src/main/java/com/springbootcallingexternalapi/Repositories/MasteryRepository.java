package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.springbootcallingexternalapi.Util.AlphaVerifier.isAlpha;

@Repository
public class MasteryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertMasteryInfo(MasteryHistoryInfoModel masteryHistoryInfoModel) throws AccountDataException {

        String sql = "INSERT INTO \"AccountMasteryHistory\" VALUES(?,?,?,?,?,?)";
        Object[] params = {masteryHistoryInfoModel.getTimestamp(),
                masteryHistoryInfoModel.getChampionId(),
                masteryHistoryInfoModel.getChampionName(),
                masteryHistoryInfoModel.getChampionPoints(),
                masteryHistoryInfoModel.getChampionLevel(),
                masteryHistoryInfoModel.getAccount()};

        try {
            jdbcTemplate.update(sql, params);
        }catch (DataAccessException e){
            throw new AccountDataException(masteryHistoryInfoModel);
        }
    }

    public List<MasteryHistoryInfoModel> AccountMasteryHistory (String account) throws AccountNotFoundException, CharacterNotAllowedException {
        String sql = "SELECT \"Account\",\"championPoints\",\"championName\",\"championId\",\"championLevel\",\"timeStamp\" FROM \"AccountMasteryHistory\" WHERE LOWER (\"Account\")=? ORDER BY \"championName\" ";
        Object[] params = {account};

        if(isAlpha (account)){
            List<MasteryHistoryInfoModel> listMastery = jdbcTemplate.query(sql, params,
                    BeanPropertyRowMapper.newInstance(MasteryHistoryInfoModel.class));
            if (account.length() == 0) {
                throw new AccountNotFoundException(account);
            } else return listMastery;
        }else throw new CharacterNotAllowedException(account);
    }
}
