package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import java.util.Arrays;

public class CurrentGameInfoBaseModel {

    private Long mapId;

    private String gameMode;

    private String gameType;

    private CurrentGameParticipantModel[] participants;

    @Override
    public String toString() {
        return "CurrentGameInfoBaseModel{" +
                "mapId=" + mapId +
                ", gameMode='" + gameMode + '\'' +
                ", gameType='" + gameType + '\'' +
                ", participants=" + Arrays.toString(participants) +
                '}';
    }

    public CurrentGameInfoBaseModel(Long mapId, String gameMode, String gameType, CurrentGameParticipantModel[] participants) {
        this.mapId = mapId;
        this.gameMode = gameMode;
        this.gameType = gameType;
        this.participants = participants;
    }

    public CurrentGameInfoBaseModel() {
    }

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public CurrentGameParticipantModel[] getParticipants() {
        return participants;
    }

    public void setParticipants(CurrentGameParticipantModel[] participants) {
        this.participants = participants;
    }
}
