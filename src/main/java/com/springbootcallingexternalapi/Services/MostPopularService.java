package com.springbootcallingexternalapi.Services;

import com.springbootcallingexternalapi.Repositories.MostPopularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MostPopularService {
    @Autowired
    MostPopularRepository mostPopularRepository;
    public void mostpopularRepository(){
        mostPopularRepository.retrieveMostPopularAccount();
    }
}
