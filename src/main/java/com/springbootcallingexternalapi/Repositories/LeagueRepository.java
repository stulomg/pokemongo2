package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.SummonerIdNotFoundException;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LeagueRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(AccountRepository.class);

    public void insertLeagueInfo(LeagueInfoModel leagueInfoModel) throws SummonerIdNotFoundException {
        String sql = "INSERT INTO \"LeagueInfo\" VALUES(?,?,?,?,?,?)";
        Object[] params = {leagueInfoModel.getLeagueId(),leagueInfoModel.getQueueType(),leagueInfoModel.getTier(),leagueInfoModel.getRank(),leagueInfoModel.getSummonerName(),leagueInfoModel.getLeaguePoints()};
        try{
            jdbcTemplate.update(sql,params);
        }catch (DataAccessException e){
            logger.info(e.getMessage());
            throw new SummonerIdNotFoundException(leagueInfoModel);
        }
    }
}
