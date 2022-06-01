package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/** Model for the maintenance status.*/
public class MaintenancesStatusModel {

  private String name;
  private String[] locales;
  private String[] maintenances;
  private String[] incidents;

  public MaintenancesStatusModel() {
  }

  /** Constructor for the maintenance status.*/
  public MaintenancesStatusModel(String name, String[] locales, String[] maintenances,
      String[] incidents) {
    this.name = name;
    this.locales = locales;
    this.maintenances = maintenances;
    this.incidents = incidents;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getLocales() {
    return locales;
  }

  public void setLocales(String[] locales) {
    this.locales = locales;
  }

  public String[] getMaintenances() {
    return maintenances;
  }

  public void setMaintenances(String[] maintenances) {
    this.maintenances = maintenances;
  }

  public String[] getIncidents() {
    return incidents;
  }

  public void setIncidents(String[] incidents) {
    this.incidents = incidents;
  }
}