package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.Exceptions.ChampionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

@Repository
public class ChampionRepository {
        @Autowired
        private JdbcTemplate jdbcTemplate;
        Logger logger = LoggerFactory.getLogger(com.springbootcallingexternalapi.Repositories.ChampionRepository.class);

    public Long retrieveChampionIdByChampionName(String championName) throws ChampionNotFoundException , ChampionMasteryNotFoundException {
        String sql = "SELECT \"ChampionId\" FROM \"Champions\" WHERE \"ChampionName\"=?" ;
        Object[] params = {championName};
        try {
            Long championId = jdbcTemplate.queryForObject(sql, params, Long.class);
            return championId;
        }catch (EmptyResultDataAccessException e) {
            throw new ChampionNotFoundException(championName);
        }catch (HttpClientErrorException e1){
            throw  new ChampionMasteryNotFoundException(championName);
        }
    }
}
