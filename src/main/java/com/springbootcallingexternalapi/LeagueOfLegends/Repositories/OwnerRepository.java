package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.springbootcallingexternalapi.LeagueOfLegends.Util.AlphaVerifier.isAlpha;

@Repository
public class OwnerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertOwner(OwnerModel owner){
        String sql = "INSERT INTO \"Owner\"(\"name\") VALUES (?)";
        Object[] params = {owner.getName()};
        jdbcTemplate.update(sql, params);
    }

    public Long retrieveOwnerIdByOwnerName(String owner) throws  CharacterNotAllowedException, OwnerNotFoundException {
        String sql = "SELECT \"id\" FROM \"Owner\" WHERE LOWER(\"name\")=?";
        Object[] params = {owner};
        if (isAlpha(owner)) {
            try {
                return jdbcTemplate.queryForObject(sql, params, Long.class);
            } catch (EmptyResultDataAccessException e) {
                throw new OwnerNotFoundException(owner);
            }
        } else throw new CharacterNotAllowedException(owner);
    }
}