package com.springbootcallingexternalapi.RestControllers;

import com.springbootcallingexternalapi.Models.AccountBaseModel;
import org.junit.Test;

public class RiotRestControllerTest {

    @Test
    public void insertAccountExitosamenteEnCasoDefault(){

        //jdbcTemplate.execute("TRUNCATE TABLE \"Accounts\"");

        AccountBaseModel baseModel = new AccountBaseModel(
                "IZFyGsu-JAEUSRVhFIZfNTn3GyxGs3Czkuu4xLF6KeDsoeY",
                "j08sf6UyWH02HuceTTo255Ej2ozXs7QDlY6AK3ES_SBic-1xR7UPB99a",
                "y38Dbbwd74qmqTouPMB64ZEdYEd0iQAHoHP_OPRlpdqkNv_FD8PAPOFdCWaTerbXeBYBgR_qGIhWCQ",
                "soyeon lover",
                4864,
                1648276400000L,
                109L
        );

        String owner = "kusi";
    }
}
