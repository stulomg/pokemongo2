package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.MostPopularModel;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.MostPopularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MostPopularService {
    @Autowired
    MostPopularRepository mostPopularRepository;

    public List<MostPopularModel> mostpopularRepository() throws NoDataException {
        return mostPopularRepository.popularAccount();
    }
}
