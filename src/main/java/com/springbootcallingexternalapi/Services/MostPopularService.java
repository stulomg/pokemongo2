package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Exceptions.MostPopularExceptions.DBException;
import com.springbootcallingexternalapi.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.Models.MostPopularModel;
import com.springbootcallingexternalapi.Repositories.MostPopularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MostPopularService {
    @Autowired
    MostPopularRepository mostPopularRepository;
    public List<MostPopularModel> mostpopularRepository() throws NoDataException, DBException {
        return mostPopularRepository.popularAccount();
    }
}
