package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.MaintenancesStatusModel;
import com.springbootcallingexternalapi.SpringBootCallingExternalApiApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.List;

@SpringBootTest(classes = ServerRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)

public class ServerStatusRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ServerRepository repository;

    @Autowired
    public ServerStatusRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        repository = new ServerRepository();
        ReflectionTestUtils.setField(repository, "jdbcTemplate", jdbcTemplate);
    }

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"ServerStatus\" RESTART IDENTITY CASCADE");
    }

    @Test
    void insertSuccessfully() {
        String[] locales = {""};
        String[] maintenances = {""};
        String[] incidents = {""};

        MaintenancesStatusModel model = new MaintenancesStatusModel(
                "",
                locales,
                maintenances,
                incidents
        );
        repository.insertServerStatus(model);

        List<MaintenancesStatusModel> resultSet = jdbcTemplate.query("SELECT * FROM \"ServerStatus\"", BeanPropertyRowMapper.newInstance(MaintenancesStatusModel.class));
        Assertions.assertEquals(1, resultSet.size());

    }
}