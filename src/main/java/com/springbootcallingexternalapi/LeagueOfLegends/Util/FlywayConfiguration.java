package com.springbootcallingexternalapi.LeagueOfLegends.Util;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * This class configure the flyway database.
 */
@Configuration
public class FlywayConfiguration {

  @Autowired
  public FlywayConfiguration(DataSource dataSource) {
    Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();
  }
}
