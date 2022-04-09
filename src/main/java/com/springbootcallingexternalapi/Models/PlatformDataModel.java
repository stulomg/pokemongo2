package com.springbootcallingexternalapi.Models;

import java.util.List;

public class PlatformDataModel {

    private String id;

    private String name;

    private List[] locales;

    private MaintenancesStatusModel[] maintenances;

    private  MaintenancesStatusModel[] incidents;

    public PlatformDataModel(String id, String name, List[] locales, MaintenancesStatusModel[] maintenances, MaintenancesStatusModel[] incidents) {
        this.id = id;
        this.name = name;
        this.locales = locales;
        this.maintenances = maintenances;
        this.incidents = incidents;
    }

    public PlatformDataModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List[] getLocales() {
        return locales;
    }

    public void setLocales(List[] locales) {
        this.locales = locales;
    }

    public MaintenancesStatusModel[] getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(MaintenancesStatusModel[] maintenances) {
        this.maintenances = maintenances;
    }

    public MaintenancesStatusModel[] getIncidents() {
        return incidents;
    }

    public void setIncidents(MaintenancesStatusModel[] incidents) {
        this.incidents = incidents;
    }
}
