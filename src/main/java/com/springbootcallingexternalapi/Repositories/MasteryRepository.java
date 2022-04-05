package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.Exceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(MasteryRepository.class);

    public void insertMasteryInfo (MasteryHistoryInfoModel masteryHistoryInfoModel){

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
            logger.info(e.getMessage());
        }
    }

    public List<MasteryHistoryInfoModel> AccountMasteryHistory (String account) throws AccountNotFoundException, CharacterNotAllowedException {
        String sql = "SELECT \"Account\",\"championPoints\",\"championName\",\"championId\",\"championLevel\",\"timeStamp\" FROM \"AccountMasteryHistory\" WHERE LOWER (\"Account\")=? ORDER BY \"championName\" ";
        Object[] params = {account};

        if(isAlpha (account)){
            List<MasteryHistoryInfoModel> lisMastery = jdbcTemplate.query(sql,params,
                    BeanPropertyRowMapper.newInstance(MasteryHistoryInfoModel.class));
            if (account.length() == 0){
                throw new AccountNotFoundException(account);
            }else return lisMastery;
        }else throw new CharacterNotAllowedException(account);
    }
}
