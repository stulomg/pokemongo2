package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Query.QuerySyntaxErrorException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.QueryModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.QueryResponseModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    @Autowired
    QueryRepository queryRepository;

    public List<QueryResponseModel> querySpecific(QueryModel queryModel) throws QuerySyntaxErrorException {
        String sql = "SELECT "+queryModel.getSelect()+" FROM \""+queryModel.getFrom()+"\" WHERE "+queryModel.getWhere();
        return queryRepository.querySpecific(queryModel,sql);
    }
}