package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QuerySyntaxErrorException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.QueryModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.QueryResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueryRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<QueryResponseModel> querySpecific(QueryModel queryModel, String sql) throws QuerySyntaxErrorException {

        String sqlSave = "INSERT INTO \"QuerySpecific\"(criterio, query) VALUES (?, ?);";
        Object[] params = {
                queryModel.getCriterio(),
                sql
        };
        try {
            List<QueryResponseModel>  querySpecific= jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(QueryResponseModel.class));
            if (querySpecific != null){
                jdbcTemplate.update(sqlSave,params);
                return querySpecific;
            }
        }catch (DataAccessException e){
            throw new QuerySyntaxErrorException(queryModel.getCriterio());
        }
        return null;
    }
}
