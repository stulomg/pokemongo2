package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.DBException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MostPopularModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

@Repository
public class MostPopularRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<MostPopularModel> popularAccount() throws NoDataException {
        String sql = "SELECT  \"Account\", \"championName\" , (SELECT \"date\"::date FROM  \"LeagueInfo\" WHERE \"summonerName\" = (SELECT \"summonerName\" FROM  \"LeagueInfo\" GROUP BY \"summonerName\" ORDER BY COUNT( \"summonerName\" ) DESC LIMIT 1) GROUP BY \"date\"::date , \"summonerName\" ORDER BY COUNT( \"date\" ) DESC LIMIT 1)\n" +
                "FROM  \"AccountMasteryHistory\"\n" +
                "WHERE \"timeStamp\" >= (now() - '1 month'::INTERVAL) and \"Account\" = (SELECT \"summonerName\" FROM  \"LeagueInfo\" GROUP BY \"summonerName\" ORDER BY COUNT( \"summonerName\" ) DESC LIMIT 1)\n" +
                "GROUP BY \"championName\",\"Account\"\n" +
                "ORDER BY MAX(\"championPoints\")- MIN(\"championPoints\") DESC LIMIT 1";

            List<MostPopularModel>  popularAccout= jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MostPopularModel.class));
            if (popularAccout.isEmpty()){
                throw new NoDataException();
            }else  {
                return popularAccout;
            }




    }
}
