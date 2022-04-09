package com.springbootcallingexternalapi.Models;

import java.awt.*;

public class MaintenancesStatusModel {

    private int id;

    private String maintenances_status;

    private String incident_severity;

    private ContentDtoModel[] titles;

    private UpdateDtoModel[] updates;

    private String created_at;

    private String archive_at;

    private String updated_at;

    private List[] platforms;

    public MaintenancesStatusModel(int id, String maintenances_status, String incident_severity, ContentDtoModel[] titles, UpdateDtoModel[] updates, String created_at, String archive_at, String updated_at, List[] platforms) {
        this.id = id;
        this.maintenances_status = maintenances_status;
        this.incident_severity = incident_severity;
        this.titles = titles;
        this.updates = updates;
        this.created_at = created_at;
        this.archive_at = archive_at;
        this.updated_at = updated_at;
        this.platforms = platforms;
    }

    public MaintenancesStatusModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaintenances_status() {
        return maintenances_status;
    }

    public void setMaintenances_status(String maintenances_status) {
        this.maintenances_status = maintenances_status;
    }

    public String getIncident_severity() {
        return incident_severity;
    }

    public void setIncident_severity(String incident_severity) {
        this.incident_severity = incident_severity;
    }

    public ContentDtoModel[] getTitles() {
        return titles;
    }

    public void setTitles(ContentDtoModel[] titles) {
        this.titles = titles;
    }

    public UpdateDtoModel[] getUpdates() {
        return updates;
    }

    public void setUpdates(UpdateDtoModel[] updates) {
        this.updates = updates;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getArchive_at() {
        return archive_at;
    }

    public void setArchive_at(String archive_at) {
        this.archive_at = archive_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List[] getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List[] platforms) {
        this.platforms = platforms;
    }
}
