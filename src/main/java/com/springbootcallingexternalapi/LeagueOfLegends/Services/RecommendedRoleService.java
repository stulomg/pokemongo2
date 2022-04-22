package com.springbootcallingexternalapi.LeagueOfLegends.Services;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.MostPopularExceptions.NoDataException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.RecommendedRole;
import com.springbootcallingexternalapi.LeagueOfLegends.Repositories.RecommendedRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendedRoleService {
    @Autowired
    RecommendedRoleRepository recommendedRoleRepository;
    public List<RecommendedRole> recommendedRoleRepository(String account1, String account2, String account3, String account4, String account5) throws NoDataException {
        return recommendedRoleRepository.recommendedRole(account1,account2,account3,account4,account5);
    }
}
