package com.springbootcallingexternalapi.LeagueOfLegends.Security.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Security.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NewUserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void insertUser(User user)  {

        String sql = "INSERT INTO \"user\" (\"name\",\"user_name\",\"email\",\"password\") VALUES(?,?,?,?,?)";
        Object[] params = {user.getName(), user.getUserName(), user.getEmail(), user.getPassword(),user.getRoles()};
        jdbcTemplate.update(sql, params);

    }
}
