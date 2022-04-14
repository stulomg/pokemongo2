package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.AccountExceptions.AccountDataException;
import com.springbootcallingexternalapi.Exceptions.MostPopularExceptions.DBException;
import com.springbootcallingexternalapi.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.Models.MostPopularModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.springbootcallingexternalapi.Util.AlphaVerifier.isAlpha;

@Repository
public class MostPopularRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<MostPopularModel> popularAccount() throws NoDataException, DBException {
        String sql = "SELECT  \"Account\", \"championName\" , (SELECT \"date\"::date FROM  \"LeagueInfo\" WHERE \"summonerName\" = (SELECT \"summonerName\" FROM  \"LeagueInfo\" GROUP BY \"summonerName\" ORDER BY COUNT( \"summonerName\" ) DESC LIMIT 1) GROUP BY \"date\"::date , \"summonerName\" ORDER BY COUNT( \"date\" ) DESC LIMIT 1)\n" +
                "FROM  \"AccountMasteryHistory\"\n" +
                "WHERE \"timeStamp\" >= (now() - '1 month'::INTERVAL) and \"Account\" = (SELECT \"summonerName\" FROM  \"LeagueInfo\" GROUP BY \"summonerName\" ORDER BY COUNT( \"summonerName\" ) DESC LIMIT 1)\n" +
                "GROUP BY \"championName\",\"Account\"\n" +
                "ORDER BY MAX(\"championPoints\")- MIN(\"championPoints\") DESC LIMIT 1";
        try {
            List<MostPopularModel>  popularAccout= jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MostPopularModel.class));
            if (popularAccout.isEmpty()){
                throw new NoDataException();
            }else  {
                return popularAccout;
            }
        }catch (DataAccessException e){
            throw new DBException();
        }



    }
}
