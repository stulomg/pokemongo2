package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QueryCriteriaExistException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QueryFilterNoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QueryInvalidParameterException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QueryNoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QuerySyntaxErrorException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.QueryModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.QueryResponseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.QueryRepository;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class host the services that let you create a custom query to our Account database.
 */
@Service
public class QueryService {

  @Autowired
  QueryRepository queryRepository;

  /**
   * This function allows you to register a custom query into our database.
   */
  public List<QueryResponseModel> specificQuery(QueryModel queryModel)
      throws QuerySyntaxErrorException, QueryInvalidParameterException, QueryCriteriaExistException,
      NoDataException {
    String[] excludedWords = {"insert", "delete", "update", "truncate", "create", "alter", "drop"};
    for (int i = 0; i < excludedWords.length; i++) {
      if (queryModel.getQuery().contains(excludedWords[i]) || queryModel.getQuery()
          .contains(excludedWords[i].toUpperCase(Locale.ROOT))) {
        throw new QueryInvalidParameterException(queryModel.getCriteria());
      }
    }
    List<QueryResponseModel> resultSpecificQuery = queryRepository.specificQuery(queryModel);
    queryRepository.newQuery(queryModel);
    return resultSpecificQuery;
  }

  public List<QueryModel> listQuery() throws QueryNoDataException {
    return queryRepository.listQuery();
  }

  public List<QueryResponseModel> selectQuery(String criteria)
      throws QueryFilterNoDataException, NoDataException, QuerySyntaxErrorException {
    QueryModel specificQuery = queryRepository.filterQuery(criteria);
    return queryRepository.specificQuery(specificQuery);
  }

}