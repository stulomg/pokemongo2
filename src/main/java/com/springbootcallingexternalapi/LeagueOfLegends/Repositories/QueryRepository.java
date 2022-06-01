package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QueryCriteriaExistException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QueryFilterNoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QueryNoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QuerySyntaxErrorException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.QueryModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.QueryResponseModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/** Scan repository class.*/

@Repository
public class QueryRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  /** Insert new query.*/

  public void newQuery(QueryModel queryModel) throws QueryCriteriaExistException {
    String sqlSave = "INSERT INTO \"SpecificQuery\"(criteria, query) VALUES (?, ?);";
    Object[] params = {
        queryModel.getCriteria(),
        queryModel.getQuery()
    };
    try {
      jdbcTemplate.update(sqlSave, params);
    } catch (DataIntegrityViolationException e) {
      throw new QueryCriteriaExistException(queryModel.getCriteria());
    }
  }

  /** Call a new query.*/

  public List<QueryResponseModel> specificQuery(QueryModel queryModel)
      throws QuerySyntaxErrorException, NoDataException {
    try {
      List<QueryResponseModel> querySpecific = jdbcTemplate.query(queryModel.getQuery(),
          BeanPropertyRowMapper.newInstance(QueryResponseModel.class));
      if (querySpecific.isEmpty()) {
        throw new NoDataException();
      }
      return querySpecific;
    } catch (DataAccessException e) {
      throw new QuerySyntaxErrorException(queryModel.getCriteria());
    } catch (NoDataException e) {
      throw new NoDataException();
    }
  }

  /** Shows a list of query.*/

  public List<QueryModel> listQuery() throws QueryNoDataException {
    String sql = "SELECT * FROM \"SpecificQuery\"";
    List<QueryModel> querySpecific = jdbcTemplate.query(sql,
        BeanPropertyRowMapper.newInstance(QueryModel.class));
    if (querySpecific.isEmpty()) {
      throw new QueryNoDataException();
    }
    return querySpecific;
  }

  /** Filter a query.*/

  public QueryModel filterQuery(String criteria) throws QueryFilterNoDataException {
    String sql = "SELECT * FROM \"SpecificQuery\" WHERE \"criteria\" = ?;";
    Object[] params = {criteria};
    try {
      QueryModel filterQuery = jdbcTemplate.queryForObject(sql, params,
          BeanPropertyRowMapper.newInstance(QueryModel.class));
      return filterQuery;
    } catch (EmptyResultDataAccessException e) {
      throw new QueryFilterNoDataException(criteria);
    }
  }
}