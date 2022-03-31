package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.SummonerIdNotFoundException;
import com.springbootcallingexternalapi.Models.LeagueInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class LeagueRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(AccountRepository.class);

    public void insertLeagueInfo(LeagueInfoModel leagueInfoModel) throws SummonerIdNotFoundException {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        String sql = "INSERT INTO \"LeagueInfo\" VALUES(?,?,?,?,?,?)";
        Object[] params = {date,leagueInfoModel.getQueueType(),leagueInfoModel.getTier(),leagueInfoModel.getRank(),leagueInfoModel.getSummonerName(),leagueInfoModel.getLeaguePoints()};
        try{
            jdbcTemplate.update(sql,params);
        }catch (DataAccessException e){
            logger.info(e.getMessage());
            throw new SummonerIdNotFoundException(leagueInfoModel);
        }
    }
    public List<LeagueInfoModel> divisionHistory(String summonerName) {

        String sql = "SELECT * FROM \"LeagueInfo\" WHERE \"summonerName\"=?" ;
        Object[] params = {summonerName};
        List<LeagueInfoModel> listLeagues = jdbcTemplate.query(sql,params,BeanPropertyRowMapper.newInstance(LeagueInfoModel.class));

        return listLeagues;

    }
}
