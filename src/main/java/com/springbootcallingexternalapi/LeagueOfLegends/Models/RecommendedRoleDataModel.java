package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class RecommendedRoleDataModel {
    private String participant1;
    private String participant2;
    private String participant3;
    private String participant4;
    private String participant5;

    public RecommendedRoleDataModel() {
    }

    public RecommendedRoleDataModel(String participant1, String participant2, String participant3, String participant4, String participant5) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.participant3 = participant3;
        this.participant4 = participant4;
        this.participant5 = participant5;
    }

    public String getParticipant1() {
        return participant1;
    }

    public void setParticipant1(String participant1) {
        this.participant1 = participant1;
    }

    public String getParticipant2() {
        return participant2;
    }

    public void setParticipant2(String participant2) {
        this.participant2 = participant2;
    }

    public String getParticipant3() {
        return participant3;
    }

    public void setParticipant3(String participant3) {
        this.participant3 = participant3;
    }

    public String getParticipant4() {
        return participant4;
    }

    public void setParticipant4(String participant4) {
        this.participant4 = participant4;
    }

    public String getParticipant5() {
        return participant5;
    }

    public void setParticipant5(String participant5) {
        this.participant5 = participant5;
    }
}
