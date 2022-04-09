package com.springbootcallingexternalapi.Models;

public class MapBaseModel {

    private Long queueId;

    private String mapName;

    private String mapDescription;

    public MapBaseModel(Long queueId, String mapName, String mapDescription) {
        this.queueId = queueId;
        this.mapName = mapName;
        this.mapDescription = mapDescription;
    }

    public MapBaseModel() {
    }

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapDescription() {
        return mapDescription;
    }

    public void setMapDescription(String mapDescription) {
        this.mapDescription = mapDescription;
    }
}
