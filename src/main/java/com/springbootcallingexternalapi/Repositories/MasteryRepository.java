package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.Exceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.Exceptions.SummonerIdNotFoundException;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import com.springbootcallingexternalapi.Models.MasteryHistoryInfoModel;
import com.springbootcallingexternalapi.Models.MasteryInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
                masteryHistoryInfoModel.getAccount()} ;
        jdbcTemplate.update(sql, params);
    }
}
