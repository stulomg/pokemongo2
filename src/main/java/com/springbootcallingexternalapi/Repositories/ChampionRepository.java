package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Models.AccountModel;
import com.springbootcallingexternalapi.Models.MasteryInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChampionRepository {
        @Autowired
        private JdbcTemplate jdbcTemplate;
        Logger logger = LoggerFactory.getLogger(com.springbootcallingexternalapi.Repositories.ChampionRepository.class);

    public String retrieveChampionByChampionId(Long championId) {
        String sql = "SELECT championName FROM \"Champions\" WHERE ChampionId=?" ;
        Object[] params = {championId};

        String championName = jdbcTemplate.queryForObject(sql,params,String.class);

        return championName;
    }
}
