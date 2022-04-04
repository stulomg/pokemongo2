package com.springbootcallingexternalapi.Repositories;

import com.springbootcallingexternalapi.Exceptions.OwnerAllreadyRegisterException;
import com.springbootcallingexternalapi.Exceptions.OwnerWrongIdException;
import com.springbootcallingexternalapi.Models.OwnerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import static com.springbootcallingexternalapi.Util.AlphaVerifier.isAlpha;

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
