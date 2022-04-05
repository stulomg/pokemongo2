package com.springbootcallingexternalapi.Repositories;
import com.springbootcallingexternalapi.Exceptions.ChampionsExceptions.ChampionMasteryNotFoundException;
import com.springbootcallingexternalapi.Exceptions.ChampionsExceptions.ChampionNotFoundException;
import com.springbootcallingexternalapi.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import static com.springbootcallingexternalapi.Util.AlphaVerifier.isAlpha;


@Repository
public class ChampionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long retrieveChampionIdByChampionName(String championName)
            throws ChampionNotFoundException, ChampionMasteryNotFoundException, CharacterNotAllowedException {
        String sql = "SELECT \"ChampionId\" FROM \"Champions\" WHERE LOWER (\"ChampionName\")=?" ;
        Object[] params = {championName};
        if (isAlpha(championName)) {
            try {
                Long championId = jdbcTemplate.queryForObject(sql, params, Long.class);
                return championId;
            } catch (EmptyResultDataAccessException e) {
                throw new ChampionNotFoundException(championName);
            } catch (HttpClientErrorException e1) {
                throw new ChampionMasteryNotFoundException(championName);
            }
        } else throw new CharacterNotAllowedException(championName);
    }
}
