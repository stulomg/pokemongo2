package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class RecommendedClashDataModel {
    private String participant;

    public RecommendedClashDataModel() {
    }

    public RecommendedClashDataModel(String participant) {
        this.participant = participant;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}
