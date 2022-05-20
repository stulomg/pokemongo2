package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.ChampionsExceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.ChampionsExceptions.ChampionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Locale;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

@Repository
public class ChampionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long retrieveChampionIdByChampionName(String championName) throws ChampionNotFoundException, ChampionMasteryNotFoundException, CharacterNotAllowedException {
        String sql = "SELECT \"ChampionId\" FROM \"Champion\" WHERE LOWER (\"ChampionName\")=?";
        Object[] params = {championName.toLowerCase(Locale.ROOT)};
        if (isAlpha(championName)) {
            try {
                return jdbcTemplate.queryForObject(sql, params, Long.class);
            } catch (EmptyResultDataAccessException e) {
                throw new ChampionNotFoundException(championName);
            } catch (HttpClientErrorException e1) {
                throw new ChampionMasteryNotFoundException(championName);
            }
        } else throw new CharacterNotAllowedException(championName);
    }
}