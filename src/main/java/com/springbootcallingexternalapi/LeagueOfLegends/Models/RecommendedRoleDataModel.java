package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class RecommendedRoleDataModel {
    private String participant;

    public RecommendedRoleDataModel() {
    }

    public RecommendedRoleDataModel(String participant) {
        this.participant = participant;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}
