package com.springbootcallingexternalapi.LeagueOfLegends.Models;

import java.util.Arrays;

public class CurrentGameInfoRuneModel {
    private Long mapId;

    private String gameMode;

    private String gameType;

    private CurrentGameRunesModel[] participants;

    @Override
    public String toString() {
        return "CurrentGameInfoRuneModel{" +
                "mapId=" + mapId +
                ", gameMode='" + gameMode + '\'' +
                ", gameType='" + gameType + '\'' +
                ", participants=" + Arrays.toString(participants) +
                '}';
    }

    public CurrentGameInfoRuneModel(Long mapId, String gameMode, String gameType, CurrentGameRunesModel[] participants) {
        this.mapId = mapId;
        this.gameMode = gameMode;
        this.gameType = gameType;
        this.participants = participants;
    }

    public CurrentGameInfoRuneModel() {
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

    public CurrentGameRunesModel[] getParticipants() {
        return participants;
    }

    public void setParticipants(CurrentGameRunesModel[] participants) {
        this.participants = participants;
    }
}
