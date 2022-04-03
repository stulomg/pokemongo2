package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MasteryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(MasteryRepository.class);

    public void insertMasteryInfo (MasteryHistoryInfoModel masteryHistoryInfoModel){

        String sql = "INSERT INTO \"AccountMasteryHistory\" VALUES(?,?,?,?,?)";
        Object[] params = {masteryHistoryInfoModel.getTimestamp(),
                masteryHistoryInfoModel.getChampionId(),
                masteryHistoryInfoModel.getChampionName(),
                masteryHistoryInfoModel.getChampionPoints(),
                masteryHistoryInfoModel.getChampionLevel()};

        try {
            jdbcTemplate.update(sql, params);
        }catch (DataAccessException e){
            logger.info(e.getMessage());
        }
    }
}
