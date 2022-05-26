package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerAlreadyExists;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions.OwnerNotFoundException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.OwnerModel;
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
import java.util.List;

@SpringBootTest(classes = OwnerRepository.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBootCallingExternalApiApplication.class)
class OwnerRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE \"Owner\" RESTART IDENTITY CASCADE");
    }

    @Test
    void insertOwner() throws OwnerAlreadyExists, CharacterNotAllowedException {
        OwnerModel newOwner = new OwnerModel(
                "testprueba"
        );
        ownerRepository.insertOwner(newOwner);
        List<OwnerModel>  resultSet = jdbcTemplate.query("SELECT * FROM \"Owner\"", BeanPropertyRowMapper.newInstance(OwnerModel.class));
        Assertions.assertEquals(1, resultSet.size());
        OwnerModel result = resultSet.get(0);
        Assertions.assertEquals(newOwner.getName(), result.getName());
    }
    @Test
    void characterNotAllowedExceptionInsertOwner() {
        OwnerModel newOwner = new OwnerModel(
                "testprueba*"
        );
        Assertions.assertThrows(CharacterNotAllowedException.class, () -> { ownerRepository.insertOwner(newOwner);});
    }
    @Test
    void ownerAlreadyExistsInsertOwner() throws OwnerAlreadyExists, CharacterNotAllowedException {
        OwnerModel newOwner = new OwnerModel(
                "testprueba"
        );
        ownerRepository.insertOwner(newOwner);
        Assertions.assertThrows(OwnerAlreadyExists.class, () -> { ownerRepository.insertOwner(newOwner);});
    }
    @Test
    void retrieveOwnerIdByOwnerName() throws CharacterNotAllowedException, OwnerNotFoundException, OwnerAlreadyExists {
        OwnerModel Owner = new OwnerModel(
                "testprueba"
        );
        ownerRepository.insertOwner(Owner);
        Long resultSet = ownerRepository.retrieveOwnerIdByOwnerName("testprueba");
        Assertions.assertEquals(1,resultSet);
    }
    @Test
    void characterNotAllowedExceptionRetrieveOwnerIdByOwnerName() {
        String Owner = new String(
                "testprueba**"
        );
        Assertions.assertThrows(CharacterNotAllowedException.class, () -> { ownerRepository.retrieveOwnerIdByOwnerName(Owner);});
    }
    @Test
    void ownerAlreadyExistsRetrieveOwnerIdByOwnerName() {
        String Owner = new String(
                "testprueba"
        );
        Assertions.assertThrows(OwnerNotFoundException.class, () -> { ownerRepository.retrieveOwnerIdByOwnerName(Owner);});
    }
}