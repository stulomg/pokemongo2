package com.springbootcallingexternalapi.LeagueOfLegends.Repositories;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.MaintenancesStatusModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/** Scan repository class.*/
@Repository
public class ServerRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /** Insert server status in to the db.*/
  public void insertServerStatus(MaintenancesStatusModel maintenancesStatusModel) {
    String sql = "INSERT INTO \"ServerStatus\" "
        + "(\"name\",\"locales\",\"maintenances\",\"incidents\") VALUES(?,?,?,?)";
    Object[] params = {
        maintenancesStatusModel.getName(),
        maintenancesStatusModel.getLocales(),
        maintenancesStatusModel.getMaintenances(),
        maintenancesStatusModel.getIncidents()
    };
    jdbcTemplate.update(sql, params);
  }
}