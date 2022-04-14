package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerAllreadyRegisterException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OwnerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createOwner(OwnerModel ownerModel) throws OwnerAllreadyRegisterException {

        String sql = "INSERT INTO \"NewOwner\" VALUES(?,?)";
        Object[] params = {ownerModel.getId(), ownerModel.getName()};
        try {
            jdbcTemplate.update(sql, params);
        } catch (DataAccessException e) {
            throw new OwnerAllreadyRegisterException(ownerModel);
        }
    }
}
