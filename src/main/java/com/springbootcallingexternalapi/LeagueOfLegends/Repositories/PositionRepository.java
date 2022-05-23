package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.AccountExceptions.AccountNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Position.PositionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Locale;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

@Repository
public class PositionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long retrievePositionIdByPositionName(String positionName) throws CharacterNotAllowedException, PositionNotFoundException {
        String sql = "SELECT id FROM \"Position\" WHERE LOWER(\"namePosition\")=?;";
        Object[] params = {positionName.toLowerCase(Locale.ROOT)};
        if (isAlpha(positionName)) {
            try {
                return jdbcTemplate.queryForObject(sql, params, Long.class);
            } catch (EmptyResultDataAccessException e) {
                throw new PositionNotFoundException(positionName);
            }
        } else throw new CharacterNotAllowedException(positionName);
    }
}